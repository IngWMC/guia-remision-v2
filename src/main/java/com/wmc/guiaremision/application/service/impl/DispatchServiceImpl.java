package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.response.ServiceResponse;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import com.wmc.guiaremision.domain.repository.DispatchRepository;
import com.wmc.guiaremision.infrastructure.client.SunatApiClient;
import com.wmc.guiaremision.infrastructure.storage.FileStorageService;
import com.wmc.guiaremision.infrastructure.security.DigitalSignatureService;
import com.wmc.guiaremision.infrastructure.ubl.document.DespatchAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {
  private final DispatchRepository dispatchRepository;
  private final CompanyRepository companyRepository;
  private final SunatApiClient sunatApiClient;
  private final FileStorageService fileStorageService;
  private final DigitalSignatureService digitalSignatureService;
  private final PdfGeneratorService pdfGeneratorService;

  @Override
  public ServiceResponse generateDispatch(Dispatch document) {
    return Optional.of(document)
        .map(this::validarNumeroDocumentoEmpresa)
        .map(this::crearEstructuraUbl)
        .map(this::generarXml)
        .map(this::firmarDocumento)
        .map(this::persistirDispatch)
        .map(this::enviarASunat)
        .map(this::generarPdf)
        .map(this::guardarArchivos)
        .map(this::crearRespuesta)
        .orElseThrow(() -> new RuntimeException("Error al generar guía de remisión"));
  }

  private Dispatch validarNumeroDocumentoEmpresa(Dispatch document) {
    return Optional.of(document)
        .filter(d -> this.companyRepository.findByRuc(d.getDocumentNumber()).isPresent())
        .orElseThrow(() -> new RuntimeException("No se encontró la empresa."));
  }

  private DespatchAdvice crearEstructuraUbl(Dispatch document) {
    return new DespatchAdvice(
        document.getNumeroDocumento(),
        document.getFechaEmision() != null ? document.getFechaEmision() : LocalDateTime.now());
  }

  private String generarXml(DespatchAdvice despatchAdvice) {
    return Optional.of(despatchAdvice)
        .map(this::marshallerToXml)
        .orElseThrow(() -> new RuntimeException("Error al generar XML"));
  }

  private String marshallerToXml(DespatchAdvice despatchAdvice) {
    try {
      JAXBContext context = JAXBContext.newInstance(DespatchAdvice.class);
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
      StringWriter writer = new StringWriter();
      marshaller.marshal(despatchAdvice, writer);
      return writer.toString();
    } catch (JAXBException e) {
      log.error("Error al generar XML de guía de remisión", e);
      throw new RuntimeException("Error al generar XML", e);
    }
  }

  private String firmarDocumento(String xml) {
    return Optional.of(xml)
        .map(this::aplicarFirmaDigital)
        .orElseThrow(() -> new RuntimeException("Error al firmar documento"));
  }

  private String aplicarFirmaDigital(String xml) {
    try {
      log.info("Aplicando firma digital real al documento");
      return digitalSignatureService.firmarDocumento(xml, "GUIA_REMISION");
    } catch (Exception e) {
      log.error("Error al aplicar firma digital", e);
      throw new RuntimeException("Error al aplicar firma digital", e);
    }
  }

  private Dispatch persistirDispatch(String xmlFirmado) {
    Dispatch dispatch = new Dispatch();
    dispatch.setXmlData(xmlFirmado);
    return dispatchRepository.save(dispatch);
  }

  private RespuestaSunatDto enviarASunat(String xmlFirmado) {
    return Optional.of(xmlFirmado)
        .map(sunatApiClient::sendToSunat)
        .map(this::convertirARespuestaSunat)
        .orElseThrow(() -> new RuntimeException("Error al enviar a SUNAT"));
  }

  private RespuestaSunatDto convertirARespuestaSunat(Dispatch dispatch) {
    return RespuestaSunatDto.builder()
        .ticket(dispatch.getTicketNumber())
        .estado("ACEPTADO")
        .cdr(dispatch.getCdrData())
        .build();
  }

  private Dispatch generarPdf(RespuestaSunatDto respuestaSunat) {
    String documentId = respuestaSunat.getTicket();
    Dispatch dispatch = dispatchRepository.findById(documentId)
        .orElseThrow(() -> new RuntimeException("No se encontró la guía para generar el PDF"));
    return pdfGeneratorService.generatePdf(dispatch);
  }

  private ArchivosGuardadosDto guardarArchivos(Dispatch dispatch) {
    return new ArchivosGuardadosDto(
        dispatch.getPdfPath(),
        dispatch.getXmlData(),
        dispatch.getCdrData());
  }

  private RespuestaGuiaDto crearRespuesta(ArchivosGuardadosDto archivos) {
    return RespuestaGuiaDto.builder()
        .numeroDocumento("T001-00000001")
        .ticket("123456789")
        .estado("ACEPTADO")
        .pdfUrl(archivos.getPdfPath())
        .xmlUrl(archivos.getXmlPath())
        .cdrUrl(archivos.getCdrPath())
        .build();
  }

  @lombok.Data
  @lombok.Builder
  private static class RespuestaSunatDto {
    private String ticket;
    private String estado;
    private String cdr;
  }

  @lombok.Data
  @lombok.Builder
  private static class ArchivosGuardadosDto {
    private String pdfPath;
    private String xmlPath;
    private String cdrPath;
  }
}
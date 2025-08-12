package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import com.wmc.guiaremision.domain.spi.file.SignaturePort;
import com.wmc.guiaremision.domain.spi.file.XmlGeneratorPort;
import com.wmc.guiaremision.domain.spi.file.dto.SignXmlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {
  private final CompanyRepository companyRepository;
  private final XmlGeneratorPort xmlGeneratorPort;
  private final SignaturePort signaturePort;

  @Override
  public ServiceResponse generateDispatch(Dispatch document) {
    log.info("Iniciando generación de guía de remisión para el documento: {}", document.getDocumentNumber());

    // Validar que el número de documento de la empresa exista
    /*CompanyEntity companyEntity = this.companyRepository
        .findByRuc(document.getDocumentNumber())
        .orElseThrow(() -> new RuntimeException("Error al generar guía de remisión"));*/

    // Generar el XML UBL de la guía de remisión
    String unsignedXml = this.xmlGeneratorPort.generateDispatchXml(document);

    // Firmar el XML generado
    String signedXml = this.SingXml(unsignedXml);

    return null;
    /*return Optional.of(document)
        .map(this::crearEstructuraUbl)
        .map(this::generarXml)
        .map(this::firmarDocumento)
        .map(this::persistirDispatch)
        .map(this::enviarASunat)
        .map(this::generarPdf)
        .map(this::guardarArchivos)
        .map(this::crearRespuesta)
        .orElseThrow(() -> new RuntimeException("Error al generar guía de remisión"));*/
  }

  private String SingXml(String unsignedXml) {
    // Cargar el archivo desde resources/templates
    String certificadoFileName = "10464271339.pfx";
    //documentos.getEmpresa().getParametros().get(0).getArchivoCertificado().trim();

    String resourcePath = Paths.get("src", "main", "resources", "templates").toString();
    File file = new File(resourcePath, certificadoFileName);

    if (!file.exists()) {
      throw new RuntimeException("No se encontró el certificado digital: " + certificadoFileName);
    }

    // Leer el archivo y convertirlo a Base64
    byte[] fileBytes = null; //resource.getFile().toPath());
    try {
      fileBytes = Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if (fileBytes.length == 0) {
      throw new RuntimeException("La empresa ingresada no tiene certificado");
    }
    String base64Cert = Base64.getEncoder().encodeToString(fileBytes);

    SignXmlRequest signXmlRequest = SignXmlRequest.builder()
        .digitalCertificate(base64Cert)
        .certificatePassword("Lima2025")
        .unsignedXmlContent(unsignedXml)
        .singleExtensionNode(true)
        .build();

    return this.signaturePort.signXml(signXmlRequest);
  }
}
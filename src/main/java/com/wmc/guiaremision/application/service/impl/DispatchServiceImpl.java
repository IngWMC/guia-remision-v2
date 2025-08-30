package com.wmc.guiaremision.application.service.impl;

import static com.wmc.guiaremision.infrastructure.common.Constant.DASH;
import static com.wmc.guiaremision.infrastructure.common.Constant.EMPTY;
import static com.wmc.guiaremision.infrastructure.common.Constant.XML_EXTENSION;
import static com.wmc.guiaremision.infrastructure.common.Constant.ZIP_EXTENSION;

import com.wmc.guiaremision.application.dto.CdrDataResponse;
import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.dto.SignXmlDocumentRequest;
import com.wmc.guiaremision.application.service.CdrReadService;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.application.service.SignatureService;
import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.entity.ParameterEntity;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import com.wmc.guiaremision.domain.repository.DocumentRepository;
import com.wmc.guiaremision.domain.repository.ParameterRepository;
import com.wmc.guiaremision.domain.spi.file.XmlGeneratorPort;
import com.wmc.guiaremision.domain.spi.sunat.SunatGreApiPort;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FectchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.infrastructure.common.Convert;
import com.wmc.guiaremision.infrastructure.common.Util;
import com.wmc.guiaremision.infrastructure.file.StoragePortImpl;
import com.wmc.guiaremision.infrastructure.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {
  private final CompanyRepository companyRepository;
  private final ParameterRepository parameterRepository;
  private final DocumentRepository documentRepository;
  private final XmlGeneratorPort xmlGeneratorPort;
  private final StoragePortImpl storagePort;
  private final SignatureService signatureService;
  private final SunatGreApiPort sunatGreApiPort;
  private final CdrReadService cdrReadService;

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public ServiceResponse generateDispatch(Dispatch document) {
    log.info("Iniciando generación de guía de remisión para el documento: {}", document.getDocumentCode());
    // TODO: Validar que el número de documento de la empresa exista
    CompanyEntity companyEntity = this.companyRepository
        .findByIdentityDocumentNumber(document.getSender().getIdentityDocumentNumber())
        .orElseThrow(() -> new BadRequestException("La empresa no se encuentra registrada"));

    ParameterEntity parameterEntity = this.parameterRepository
        .findByCompanyId(companyEntity.getCompanyId())
        .orElseThrow(() -> new BadRequestException("No se encontró el parámetro para la empresa"));

    // TODO: Generar el XML UBL de la guía de remisión
    String unsignedXmlContent = this.xmlGeneratorPort.generateDispatchXml(document);

    // TODO: Guardar el XML sin firmar
    Integer documentId = this.saveDispatch(document, unsignedXmlContent, companyEntity.getCompanyId(),
        parameterEntity.getUnsignedXmlFilePath());

    // TODO: Firmar el XML generado
    SignXmlDocumentRequest signXmlDocumentRequest = SignXmlDocumentRequest.builder()
        .documentId(documentId)
        .unsignedXmlContent(unsignedXmlContent)
        .signedXmlFilePath(parameterEntity.getSignedXmlFilePath())
        .certificateName(parameterEntity.getCertificateName())
        .certificatePassword(parameterEntity.getCertificatePassword())
        .certificateFilePath(parameterEntity.getCertificateFilePath())
        .identityDocumentNumber(document.getSender().getIdentityDocumentNumber())
        .documentType(document.getDocumentType().getCodigo())
        .documentCode(document.getDocumentCode())
        .build();

    String signedXmlContent = this.signatureService.signXmlDocument(signXmlDocumentRequest);

    // TODO: Enviar el XML firmado a SUNAT
    TokenRequest tokenRequest = TokenRequest.builder()
        .clientId(companyEntity.getClientId())
        .clientSecret(companyEntity.getClientSecret())
        .username(companyEntity.getSolUser())
        .password(companyEntity.getSolPassword())
        .build();

    String fileName = document.getSender().getIdentityDocumentNumber()
        .concat(DASH).concat(document.getDocumentType().getCodigo())
        .concat(DASH).concat(document.getDocumentCode());
    String zippedXmlContent = Util.generateZip(signedXmlContent, fileName.concat(XML_EXTENSION));
    String hashZip = Util.calculateZipSha256Hash(zippedXmlContent);

    SendDispatchRequest sendDispatchRequest = SendDispatchRequest.builder()
        .numRucEmisor(document.getSender().getIdentityDocumentNumber())
        .codCpe(document.getDocumentType().getCodigo())
        .numSerie(document.getDocumentSeries())
        .numCpe(document.getDocumentNumber())
        .archivo(SendDispatchRequest.Archivo.builder()
            .nomArchivo(fileName.concat(ZIP_EXTENSION))
            .arcGreZip(zippedXmlContent)
            .hashZip(hashZip)
            .build())
        .build();
    FectchCdrResponse response = sunatGreApiPort.sendGreAndFetchCdr(tokenRequest, sendDispatchRequest);
    response = sunatGreApiPort.procesarCdr(response,
        cdr -> {
          log.info("CDR recibido: {}", cdr);
          return cdr;
        },
        errorMessage -> {
          log.error("Error al procesar CDR: {}", errorMessage);
          throw new BadRequestException(errorMessage);
        });

    String cdrXmlContent = this.cdrReadService.getCdrXmlContent(response.getArcCdr());
    CdrDataResponse cdrData = this.cdrReadService.getCdrData(cdrXmlContent);

    // TODO: Guardar el CDR y actualizar el estado del documento
    return null;
    /*
     * return Optional.of(document)
     * .map(this::crearEstructuraUbl)
     * .map(this::generarXml)
     * .map(this::firmarDocumento)
     * .map(this::persistirDispatch)
     * .map(this::enviarASunat)
     * .map(this::generarPdf)
     * .map(this::guardarArchivos)
     * .map(this::crearRespuesta)
     * .orElseThrow(() -> new
     * RuntimeException("Error al generar guía de remisión"));
     */
  }

  @Override
  @Transactional
  public Integer saveDispatch(Dispatch document, String unsignedXml, Integer companyId, String unsignedXmlPath) {
    String unsignedXmlPhysicalFileName = UUID.randomUUID().toString().replace(DASH, EMPTY)
        .concat(XML_EXTENSION);

    return Optional.of(document)
        .map(doc -> this.validateUnsignedXmlPath(unsignedXmlPath))
        .map(xmlPath -> this.storagePort.saveFile(xmlPath, unsignedXmlPhysicalFileName, unsignedXml))
        .map(isSave -> this.createDocumentEntity(document, companyId, unsignedXmlPhysicalFileName))
        .map(this.documentRepository::save)
        .map(DocumentEntity::getDocumentId)
        .orElseThrow(() -> new BadRequestException("Error al guardar el dispatch"));
  }

  /**
   * Valida y obtiene la ruta del XML sin firmar.
   */
  private String validateUnsignedXmlPath(String unsignedXmlPath) {
    return Optional.ofNullable(unsignedXmlPath)
        .filter(path -> !path.isEmpty())
        .orElseThrow(() -> new BadRequestException("La ruta del XML sin firmar no puede estar vacía"));
  }

  /**
   * Crea la entidad DocumentEntity con los datos del dispatch.
   */
  private DocumentEntity createDocumentEntity(Dispatch document, Integer companyId,
      String unsignedXmlPhysicalFileName) {
    String unsignedXmlFileName = document.getDocumentCode().concat(XML_EXTENSION);
    String json = Convert.convertObjectToJson(document);

    return DocumentEntity.builder()
        .companyId(companyId)
        .DocumentType(document.getDocumentType().getCodigo())
        .documentCode(document.getDocumentCode())
        .issueDate(LocalDateTime.now())
        .sunatStatusId(1)
        .unsignedXmlFileName(unsignedXmlFileName)
        .unsignedXmlPhysicalFileName(unsignedXmlPhysicalFileName)
        .json(json)
        .userCreate(1)
        .build();
  }
}
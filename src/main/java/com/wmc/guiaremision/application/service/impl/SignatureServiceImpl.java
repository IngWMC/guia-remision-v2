package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.SignXmlDocumentRequest;
import com.wmc.guiaremision.application.service.SignatureService;
import com.wmc.guiaremision.domain.repository.DocumentRepository;
import com.wmc.guiaremision.domain.spi.file.SignaturePort;
import com.wmc.guiaremision.domain.spi.file.StoragePort;
import com.wmc.guiaremision.domain.spi.file.dto.SignXmlRequest;
import com.wmc.guiaremision.infrastructure.common.Util;
import com.wmc.guiaremision.infrastructure.config.property.StorageProperty;
import com.wmc.guiaremision.infrastructure.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static com.wmc.guiaremision.infrastructure.common.Constant.DASH;
import static com.wmc.guiaremision.infrastructure.common.Constant.EMPTY;
import static com.wmc.guiaremision.infrastructure.common.Constant.XML_EXTENSION;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignatureServiceImpl implements SignatureService {
  private final DocumentRepository documentRepository;
  private final SignaturePort signaturePort;
  private final StoragePort storagePort;
  private final StorageProperty storageProperty;

  @Override
  public String signXmlDocument(SignXmlDocumentRequest request) {
    return Optional.ofNullable(request)
        .map(this::loadCertificate)
        .map(certificateBase64 -> this.signXml(request, certificateBase64))
        .map(signedXml -> this.saveSignedXmlAndUpdateDocument(request, signedXml))
        .orElseThrow(() -> new BadRequestException("Error al firmar el XML"));
  }

  /**
   * Carga el certificado digital desde el archivo.
   */
  private String loadCertificate(SignXmlDocumentRequest request) {
    String resourcePath = Util.getResourcePath(
        storageProperty.getBasePath(), request.getCertificateFilePath()).toString();

    return Optional.of(new File(resourcePath, request.getCertificateName()))
        .filter(File::exists)
        .map(this::readFileBytes)
        .filter(bytes -> bytes.length > 0)
        .map(Base64.getEncoder()::encodeToString)
        .orElseThrow(() -> new BadRequestException("No se pudo cargar el certificado digital."));
  }

  /**
   * Firma el documento XML.
   */
  private String signXml(SignXmlDocumentRequest request, String certificateBase64) {
    SignXmlRequest signXmlRequest = SignXmlRequest.builder()
        .certificateBase64(certificateBase64)
        .certificatePassword(request.getCertificatePassword())
        .unsignedXmlContent(request.getUnsignedXmlContent())
        .singleExtensionNode(true)
        .build();

    return signaturePort.signXml(signXmlRequest);
  }

  /**
   * Guarda el XML firmado y actualiza el documento en la base de datos.
   */
  private String saveSignedXmlAndUpdateDocument(SignXmlDocumentRequest request, String signedXmlContent) {
    String signedXmlPhysicalFileName = UUID.randomUUID().toString()
        .replace(DASH, EMPTY).concat(XML_EXTENSION);
    String signedXmlFileName = request.getIdentityDocumentNumber()
        .concat(DASH).concat(request.getDocumentType())
        .concat(DASH).concat(request.getDocumentCode())
        .concat(XML_EXTENSION);

    this.storagePort.saveFile(request.getSignedXmlFilePath(), signedXmlPhysicalFileName, signedXmlContent);
    this.documentRepository.updateSignedXmlData(
        request.getDocumentId(),
        signedXmlFileName,
        signedXmlPhysicalFileName);

    return signedXmlContent;
  }

  /**
   * Lee los bytes del archivo de certificado.
   */
  private byte[] readFileBytes(File file) {
    try {
      return Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      throw new BadRequestException("Error al leer el certificado: " + e.getMessage());
    }
  }
}

package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.FileService;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.entity.ParameterEntity;
import com.wmc.guiaremision.domain.model.enums.FileTypeEnum;
import com.wmc.guiaremision.domain.repository.DocumentRepository;
import com.wmc.guiaremision.domain.repository.ParameterRepository;
import com.wmc.guiaremision.shared.common.Convert;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.infrastructure.config.property.StorageProperty;
import com.wmc.guiaremision.shared.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Servicio para la gestión y descarga de archivos generados.
 * Maneja la descarga de PDFs, XMLs firmados y CDRs de SUNAT.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final DocumentRepository documentRepository;
  private final ParameterRepository parameterRepository;
  private final StorageProperty storageProperty;

  /**
   * Descarga un archivo por su ID de solicitud y tipo.
   *
   * @param requestId ID único de la solicitud del documento
   * @param fileType  tipo de archivo (PDF, XML, CDR)
   * @return contenido del archivo en bytes
   * @throws BadRequestException si el archivo no existe o el tipo no es válido
   */
  @Override
  public byte[] download(String requestId, String fileType) {
    DocumentEntity document = this.documentRepository.findByRequestId(requestId)
        .orElseThrow(() -> new BadRequestException("No existe el archivo solicitado"));
    ParameterEntity parameter = this.parameterRepository.findByCompanyId(document.getCompanyId())
        .orElseThrow(() -> new BadRequestException("No se encontró el parámetro para la empresa"));

    Map<String, String> fileInfo = getFileInfo(fileType, document, parameter);
    String filePath = fileInfo.get("path");
    String fileName = fileInfo.get("name");
    String basePath = this.storageProperty.getBasePath();
    String fileBase64 = Util.loadResourceFile(basePath, filePath, fileName);

    return Convert.convertBase64ToByteArray(fileBase64);
  }

  /**
   * Obtiene la información de ruta y nombre del archivo según su tipo.
   *
   * @param fileType  tipo de archivo solicitado
   * @param document  entidad del documento
   * @param parameter parámetros de configuración de la empresa
   * @return mapa con la ruta y nombre del archivo
   * @throws BadRequestException si el tipo de archivo no es soportado
   */
  private Map<String, String> getFileInfo(String fileType,
                                          DocumentEntity document,
                                          ParameterEntity parameter) {
    Map<FileTypeEnum, Map<String, String>> FILE_PATHS = Map.of(
        FileTypeEnum.PDF, Map.of(
            "path", parameter.getPdfFilePath(),
            "name", document.getPdfPhysicalFileName()),
        FileTypeEnum.XML, Map.of(
            "path", parameter.getSignedXmlFilePath(),
            "name", document.getSignedXmlPhysicalFileName()),
        FileTypeEnum.CDR, Map.of(
            "path", parameter.getCdrFilePath(),
            "name", document.getCdrPhysicalFileName()));

    FileTypeEnum file = FileTypeEnum.fromCode(fileType);
    return Optional.ofNullable(FILE_PATHS.get(file))
        .orElseThrow(() -> new BadRequestException("Tipo de archivo no soportado"));
  }
}

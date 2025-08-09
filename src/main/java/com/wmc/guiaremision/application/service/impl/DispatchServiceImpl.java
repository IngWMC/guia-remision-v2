package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.domain.dto.XmlDocumentResponse;
import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import com.wmc.guiaremision.domain.spi.file.XmlGeneratorPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {
  private final CompanyRepository companyRepository;
  private final XmlGeneratorPort xmlGeneratorPort;

  @Override
  public ServiceResponse generateDispatch(Dispatch document) {
    log.info("Iniciando generación de guía de remisión para el documento: {}", document.getDocumentNumber());

    // Validar que el número de documento de la empresa exista
    /*CompanyEntity companyEntity = this.companyRepository
        .findByRuc(document.getDocumentNumber())
        .orElseThrow(() -> new RuntimeException("Error al generar guía de remisión"));*/

    // Generar el XML UBL de la guía de remisión
    XmlDocumentResponse xmlDocumentResponse = this.xmlGeneratorPort.generateDispatchXml(document);
    if (xmlDocumentResponse.getSuccess()) {

    }

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
}
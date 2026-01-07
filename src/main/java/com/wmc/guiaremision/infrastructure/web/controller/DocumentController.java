package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DocumentService;
import com.wmc.guiaremision.infrastructure.web.dto.request.DocumentQueryParamRequest;
import com.wmc.guiaremision.infrastructure.web.mapper.DocumentMapper;
import com.wmc.guiaremision.infrastructure.web.mapper.ResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documentos")
public class DocumentController {

  private final ResponseMapper responseMapper;
  private final DocumentMapper documentMapper;
  private final DocumentService documentService;

  @GetMapping
  public ResponseEntity<ServiceResponse> findAll(DocumentQueryParamRequest queryParam) {
    log.info("Inicio :: DocumentController :: findAll");

    return Optional.of(queryParam)
        .map(this.documentMapper::toDocumentFindAllRequest)
        .map(this.documentService::findAll)
        .map(this.documentMapper::toDocumentFindAllResponse)
        .map(this.responseMapper::toServiceResponseOkWithList)
        .map(ResponseEntity.ok()::body)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.toServiceResponseError()));
  }
}

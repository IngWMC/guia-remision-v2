package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DocumentService;
import com.wmc.guiaremision.infrastructure.web.dto.request.DocumentFilterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documentos")
public class DocumentController {
  private final DocumentService documentService;

  @PostMapping
  public ResponseEntity<ServiceResponse> findAll(DocumentFilterDto filter,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "Id") String sortBy,
                                                 @RequestParam(defaultValue = "asc") String sortDir) {
    return ResponseEntity.ok().body(null);
  }
}

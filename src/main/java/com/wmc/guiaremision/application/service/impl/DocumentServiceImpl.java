package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.DocumentFindAllRequest;
import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DocumentService;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.domain.repository.DocumentRepository;
import com.wmc.guiaremision.shared.common.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
  private final DocumentRepository documentRepository;

  @Override
  public ServiceResponse findAll(DocumentFindAllRequest request,
                                 int page,
                                 int size,
                                 String sortBy,
                                 String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("desc")
        ? Sort.by(sortBy).descending()
        : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page, size, sort);
    String documentType = TipoDocumentoEnum.GUIA_REMISION_REMITENTE.getCodigo();

    Page<DocumentEntity> result = this.documentRepository.findAll(
        1,
        documentType,
        request.getDocumentCode(),
        request.getStartDate(),
        request.getEndDate(),
        "P", // request.getStatusSunat(),
        pageable);

    List<Dispatch> dispatches = result.getContent()
        .stream()
        .map(document -> Convert.convertJsonToObject(document.getJson(), Dispatch.class))
        .toList();

    return ServiceResponse.builder()
        .build();
  }
}

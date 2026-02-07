package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.DocumentFindAllRequest;
import com.wmc.guiaremision.application.dto.FindAllResponse;
import com.wmc.guiaremision.application.service.DocumentService;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.enums.SunatStatusEnum;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
  private final DocumentRepository documentRepository;

  @Override
  public FindAllResponse<Dispatch> findAll(DocumentFindAllRequest request) {
    Sort sort = request.getSortDir().equalsIgnoreCase("desc")
        ? Sort.by(request.getSortBy()).descending()
        : Sort.by(request.getSortBy()).ascending();
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
    String documentType = TipoDocumentoEnum.GUIA_REMISION_REMITENTE.getCodigo();

    String sunatStatus = Optional.ofNullable(request.getStatusSunat())
        .map(SunatStatusEnum::getCode)
        .orElse(null);

    Page<DocumentEntity> result = this.documentRepository.findAll(
        10,
        documentType,
        request.getDocumentCode(),
        request.getStartDate(),
        request.getEndDate(),
        sunatStatus,
        pageable);

    List<Dispatch> dispatches = result.getContent()
        .stream()
        .map(document -> {
          Dispatch dispatch = Convert.convertJsonToObject(document.getJson(), Dispatch.class);
          dispatch.setSunatStatus(document.getSunatStatus());
          dispatch.setRequestId(document.getRequestId());
          return dispatch;
        })
        .toList();

    return new FindAllResponse<>(
        dispatches,
        result.getNumber(),
        result.getSize(),
        result.getTotalElements(),
        result.getTotalPages(),
        result.hasNext(),
        result.hasPrevious());
  }
}

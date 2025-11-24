package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.DocumentFindAllRequest;
import com.wmc.guiaremision.application.dto.DocumentFindAllResponse;
import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import org.springframework.data.domain.Page;

public interface DocumentService {
  DocumentFindAllResponse findAll(DocumentFindAllRequest request);
}

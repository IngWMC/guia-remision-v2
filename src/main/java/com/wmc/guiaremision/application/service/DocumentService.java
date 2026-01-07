package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.DocumentFindAllRequest;
import com.wmc.guiaremision.application.dto.FindAllResponse;
import com.wmc.guiaremision.domain.model.Dispatch;

public interface DocumentService {
  FindAllResponse<Dispatch> findAll(DocumentFindAllRequest request);
}

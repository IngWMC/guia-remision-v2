package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.CompanyFindAllRequest;
import com.wmc.guiaremision.application.dto.FindAllResponse;
import com.wmc.guiaremision.domain.entity.CompanyEntity;

public interface CompanyService {
  FindAllResponse<CompanyEntity> findAll(CompanyFindAllRequest request);
  CompanyEntity save(CompanyEntity company);
  CompanyEntity findByIdentityDocumentNumber(String identityDocumentNumber);
}

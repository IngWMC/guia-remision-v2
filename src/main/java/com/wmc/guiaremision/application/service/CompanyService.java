package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.entity.CompanyEntity;

public interface CompanyService {
  CompanyEntity save(CompanyEntity company);
  CompanyEntity findByIdentityDocumentNumber(String identityDocumentNumber);
}

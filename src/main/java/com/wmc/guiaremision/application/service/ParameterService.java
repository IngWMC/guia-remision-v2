package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.entity.ParameterEntity;

public interface ParameterService {
  ParameterEntity save(ParameterEntity parameterEntity, String rucCompany);
  ParameterEntity findByCompanyId(Integer companyId);
}

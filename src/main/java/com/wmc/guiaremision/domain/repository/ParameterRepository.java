package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.ParameterEntity;

import java.util.Optional;

public interface ParameterRepository {
  Optional<ParameterEntity> getParameterByCompanyId(Integer companyId);
}

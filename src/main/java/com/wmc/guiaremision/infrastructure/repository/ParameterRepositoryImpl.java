package com.wmc.guiaremision.infrastructure.repository;

import com.wmc.guiaremision.domain.entity.ParameterEntity;
import com.wmc.guiaremision.domain.repository.ParameterRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adaptador (implementación) del repositorio de Parameter en infraestructura
 */
@Repository
public interface ParameterRepositoryImpl extends JpaRepository<ParameterEntity, Integer>, ParameterRepository {
  @Override
  Optional<ParameterEntity> findByCompanyId(Integer companyId);
}

package com.wmc.guiaremision.infrastructure.adapter.repository;

import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adaptador (implementación) del repositorio de Company en infraestructura
 */
@Repository
public interface CompanyRepositoryAdapter
    extends JpaRepository<CompanyEntity, Integer>, CompanyRepository {

  @Override
  Optional<CompanyEntity> findByIdentityDocumentNumber(String identityDocumentNumber);
}
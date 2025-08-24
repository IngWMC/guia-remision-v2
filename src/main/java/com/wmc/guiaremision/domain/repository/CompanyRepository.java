package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.CompanyEntity;

import java.util.Optional;

/**
 * Puerto (interface) para el repositorio de Company en el dominio
 */
public interface CompanyRepository {
  Optional<CompanyEntity> findByIdentityDocumentNumber(String identityDocumentNumber);
}
package com.wmc.guiaremision.infrastructure.adapter.repository;

import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

  @Query("SELECT c FROM CompanyEntity c " +
      "WHERE (:identityDocumentType IS NULL OR c.identityDocumentType = :identityDocumentType) " +
      "AND (:identityDocumentNumber IS NULL OR c.identityDocumentNumber LIKE CONCAT('%', :identityDocumentNumber, '%')) " +
      "AND (:legalName IS NULL OR c.legalName LIKE CONCAT('%', :legalName, '%')) " +
      "AND (:status IS NULL OR c.status = :status)")
  Page<CompanyEntity> findAll(
      @Param("identityDocumentType") Integer identityDocumentType,
      @Param("identityDocumentNumber") String identityDocumentNumber,
      @Param("legalName") String legalName,
      @Param("status") String status,
      Pageable pageable);
}
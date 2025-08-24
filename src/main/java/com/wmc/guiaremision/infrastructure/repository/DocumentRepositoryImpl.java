package com.wmc.guiaremision.infrastructure.repository;

import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.repository.DocumentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adaptador (implementación) del repositorio de Document en infraestructura
 */
@Repository
public interface DocumentRepositoryImpl extends JpaRepository<DocumentEntity, Integer>, DocumentRepository {

  @Override
  @Modifying
  @Query("UPDATE DocumentEntity d SET " +
      "d.signedXmlFileName = :signedXmlFileName, " +
      "d.signedXmlPhysicalFileName = :signedXmlPhysicalFileName " +
      "WHERE d.documentId = :documentId")
  int updateSignedXmlData(
      @Param("documentId") Integer documentId,
      @Param("signedXmlFileName") String signedXmlFileName,
      @Param("signedXmlPhysicalFileName") String signedXmlPhysicalFileName);

  @Override
  Optional<DocumentEntity> findById(Integer documentId);
}

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
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("UPDATE DocumentEntity d SET " +
      "d.signedXmlFileName = :signedXmlFileName, " +
      "d.signedXmlPhysicalFileName = :signedXmlPhysicalFileName " +
      "WHERE d.documentId = :documentId")
  int updateSignedXmlData(
      @Param("documentId") Integer documentId,
      @Param("signedXmlFileName") String signedXmlFileName,
      @Param("signedXmlPhysicalFileName") String signedXmlPhysicalFileName);

  @Override
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("UPDATE DocumentEntity d SET " +
      "d.cdrFileName = :cdrFileName, " +
      "d.cdrPhysicalFileName = :cdrPhysicalFileName, " +
      "d.cdrPhysicalFileName = :cdrPhysicalFileName, " +
      "d.ticketSunat = :ticketSunat, " +
      "d.sunatStatusId = :sunatStatusId " +
      "WHERE d.documentId = :documentId")
  int updateCdrData(
      @Param("documentId") Integer documentId,
      @Param("cdrFileName") String cdrFileName,
      @Param("cdrPhysicalFileName") String cdrPhysicalFileName,
      @Param("ticketSunat") String ticketSunat,
      @Param("sunatStatusId") Integer sunatStatusId);

  @Override
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("UPDATE DocumentEntity d SET " +
      "d.pdfFileName = :pdfFileName, " +
      "d.pdfPhysicalFileName = :pdfPhysicalFileName " +
      "WHERE d.documentId = :documentId")
  int updatePdfData(
      @Param("documentId") Integer documentId,
      @Param("pdfFileName") String pdfFileName,
      @Param("pdfPhysicalFileName") String pdfPhysicalFileName);

  @Override
  Optional<DocumentEntity> findById(Integer documentId);
}

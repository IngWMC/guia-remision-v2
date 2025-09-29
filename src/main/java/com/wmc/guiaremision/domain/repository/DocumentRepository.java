package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DocumentRepository {
  DocumentEntity save(DocumentEntity document);

  int updateSignedXmlData(Integer documentId,
                          String signedXmlFileName,
                          String signedXmlPhysicalFileName);

  int updateCdrData(Integer documentId,
                    String cdrFileName,
                    String cdrPhysicalFileName,
                    String ticketSunat,
                    Integer sunatStatusId);

  int updatePdfData(Integer documentId,
                    String pdfFileName,
                    String pdfPhysicalFileName);

  Optional<DocumentEntity> findById(Integer documentId);

  Optional<DocumentEntity> findByRequestId(String requestId);

  Page<DocumentEntity> findAll(Integer companyId, Pageable pageable);
}

package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.DocumentEntity;

import java.util.Optional;

public interface DocumentRepository {
  DocumentEntity save(DocumentEntity document);
  int updateSignedXmlData(Integer documentId, String signedXmlFileName, String signedXmlPhysicalFileName);
  Optional<DocumentEntity> findById(Integer documentId);
}

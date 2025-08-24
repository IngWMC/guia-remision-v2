package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.SignXmlDocumentRequest;

public interface SignatureService {
  String signXmlDocument(SignXmlDocumentRequest request);
}

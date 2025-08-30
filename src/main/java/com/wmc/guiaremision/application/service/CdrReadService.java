package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.CdrDataResponse;

public interface CdrReadService {
  String getCdrXmlContent(String cdrFileContent);
  CdrDataResponse getCdrData(String cdrXmlContent);
}

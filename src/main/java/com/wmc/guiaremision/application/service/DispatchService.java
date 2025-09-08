package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.model.Dispatch;

public interface DispatchService {
    ServiceResponse generateDispatch(Dispatch document);
    DocumentEntity saveDispatch(Dispatch document, String unsignedXml, Integer companyId, String unsignedXmlPath);
}

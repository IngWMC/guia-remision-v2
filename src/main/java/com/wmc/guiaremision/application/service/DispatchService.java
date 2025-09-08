package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.domain.entity.DocumentEntity;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FectchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;

public interface DispatchService {
    ServiceResponse generateDispatch(Dispatch document);
    DocumentEntity saveDispatch(Dispatch document, String unsignedXml, Integer companyId, String unsignedXmlPath);
    FectchCdrResponse sendDispatch(TokenRequest tokenRequest, SendDispatchRequest sendDispatchRequest);
}

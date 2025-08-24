package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;

public interface SendDispatchService {
  String sendDocument(TokenRequest tokenRequest, SendDispatchRequest sendDispatchRequest);
}

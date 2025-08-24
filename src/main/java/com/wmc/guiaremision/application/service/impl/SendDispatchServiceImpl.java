package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.SendDispatchService;
import com.wmc.guiaremision.domain.spi.sunat.SunatGreApiPort;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FectchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.infrastructure.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendDispatchServiceImpl implements SendDispatchService {
  private final SunatGreApiPort sunatGreApiPort;

  @Override
  public String sendDocument(TokenRequest tokenRequest, SendDispatchRequest sendDispatchRequest) {

    return "";
  }
}

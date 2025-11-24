package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.SunatGreService;
import com.wmc.guiaremision.domain.spi.sunat.SunatGreApiPort;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FetchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SunatGreServiceImpl implements SunatGreService {
  private final SunatGreApiPort sunatGreApiPort;

  @Override
  public TokenResponse getToken(TokenRequest request) {
    return this.sunatGreApiPort.getToken(request);
  }

  @Override
  public SendDispatchResponse sendDispatch(SendDispatchRequest request) {
    return this.sunatGreApiPort.sendDispatch(request);
  }

  @Override
  public FetchCdrResponse fetchCdr(String numTicket, String accessToken) {
    return this.sunatGreApiPort.fetchCdr(numTicket, accessToken);
  }

  @Override
  public FetchCdrResponse sendGreAndFetchCdr(TokenRequest tokenRequest,
                                             SendDispatchRequest request) {
    // Obtener el token
    TokenResponse tokenResponse = this.getToken(tokenRequest);
    if (tokenResponse.isSuccess()) {
      String accessToken = tokenResponse.getAccess_token();
      // Enviar la GRE
      SendDispatchResponse sendDispatchResponse = this.sendDispatch(request);
      if (sendDispatchResponse.isSuccess()) {
        String numTicket = sendDispatchResponse.getNumTicket();
        // Consultar el CDR
        return this.fetchCdr(numTicket, accessToken);
      }
    } else {
      log.error("Error al obtener el token de SUNAT: {}", tokenResponse.getErrorMessage());
    }

    return null;
  }
}

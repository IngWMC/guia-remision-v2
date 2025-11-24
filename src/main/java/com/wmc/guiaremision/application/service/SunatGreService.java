package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FetchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenResponse;

public interface SunatGreService {
  /**
   * Obtiene un token de autenticación de SUNAT.
   *
   * @param request DTO con las credenciales para obtener el token
   * @return TokenResponse con la respuesta del token
   * @throws RuntimeException si hay error en la comunicación con SUNAT
   */
  TokenResponse getToken(TokenRequest request);

  /**
   * Envía un comprobante (GRE) a SUNAT.
   *
   * @param request DTO con la información del archivo a enviar
   * @return SendDispatchResponse con la respuesta del envío
   * @throws RuntimeException si hay error en la comunicación con SUNAT
   */
  SendDispatchResponse sendDispatch(SendDispatchRequest request);

  /**
   * Consulta el estado de un comprobante enviado a SUNAT.
   *
   * @param numTicket   Número de ticket obtenido del método enviarComprobante
   * @param accessToken Token de autenticación obtenido previamente
   * @return ConsultarComprobanteResponse con la respuesta de la consulta
   * @throws RuntimeException si hay error en la comunicación con SUNAT
   */
  FetchCdrResponse fetchCdr(String numTicket, String accessToken);

  /**
   * Método de conveniencia para enviar una GRE completa en un solo flujo.
   * Obtiene token, envía comprobante y consulta el estado usando programación
   * funcional.
   *
   * @param tokenRequest DTO con las credenciales para obtener el token
   * @param request      DTO con la información del archivo a enviar
   * @return GreProcessResult con el resultado completo del proceso
   */
  FetchCdrResponse sendGreAndFetchCdr(TokenRequest tokenRequest, SendDispatchRequest request);
}

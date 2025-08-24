package com.wmc.guiaremision.domain.spi.sunat;

import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FectchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenResponse;

import java.util.Optional;
import java.util.function.Function;

/**
 * Contrato para el cliente de la API REST de SUNAT específico para Guías de
 * Remisión Electrónica (GRE).
 * Implementa los patrones SOLID y programación funcional sin CompletableFuture.
 * 
 * @author WMC
 * @version 1.0
 */
public interface SunatGreApiPort {

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
  FectchCdrResponse fetchCdr(String numTicket, String accessToken);

  /**
   * Método de conveniencia para enviar una GRE completa en un solo flujo.
   * Obtiene token, envía comprobante y consulta el estado usando programación
   * funcional.
   * 
   * @param tokenRequest DTO con las credenciales para obtener el token
   * @param request      DTO con la información del archivo a enviar
   * @return GreProcessResult con el resultado completo del proceso
   */
  default FectchCdrResponse sendGreAndFetchCdr(
      TokenRequest tokenRequest,
      SendDispatchRequest request) {
    // Programación funcional: encadenamiento de operaciones
    return Optional.of(tokenRequest)
        .map(this::getToken)
        .filter(TokenResponse::isSuccess)
        .map(tokenResponse -> {
          request.setAccessToken(tokenResponse.getAccess_token());
          return this.sendDispatch(request);
        })
        .filter(SendDispatchResponse::isSuccess)
        .map(sendDispatchResponse -> {
          // Obtener nuevo token para consulta
          TokenResponse newToken = getToken(tokenRequest);
          if (newToken.isSuccess()) {
            return this.fetchCdr(sendDispatchResponse.getNumTicket(),
                newToken.getAccess_token());
          } else {
            throw new RuntimeException("Error al obtener token para consulta: " + newToken.getErrorMessage());
          }
        })
        .orElseThrow(() -> new RuntimeException("Error en el proceso de envío de GRE"));
  }

  /**
   * Método funcional para validar y procesar una respuesta de token.
   * 
   * @param tokenResponse Respuesta del token
   * @param onSuccess     Función a ejecutar si el token es válido
   * @param onError       Función a ejecutar si hay error
   * @return Resultado del procesamiento
   */
  default <T> T procesarToken(
      TokenResponse tokenResponse,
      Function<String, T> onSuccess,
      Function<String, T> onError) {
    return Optional.of(tokenResponse)
        .filter(TokenResponse::isSuccess)
        .map(TokenResponse::getAccess_token)
        .map(onSuccess)
        .orElseGet(() -> onError.apply(
            Optional.ofNullable(tokenResponse.getErrorMessage())
                .orElse("Error desconocido al obtener token")));
  }

  /**
   * Método funcional para validar y procesar una respuesta de envío.
   * 
   * @param envioResponse Respuesta del envío
   * @param onSuccess     Función a ejecutar si el envío es exitoso
   * @param onError       Función a ejecutar si hay error
   * @return Resultado del procesamiento
   */
  default <T> T procesarEnvio(
      SendDispatchResponse envioResponse,
      Function<String, T> onSuccess,
      Function<String, T> onError) {
    return Optional.of(envioResponse)
        .filter(SendDispatchResponse::isSuccess)
        .map(SendDispatchResponse::getNumTicket)
        .map(onSuccess)
        .orElseGet(() -> onError.apply(
            Optional.ofNullable(envioResponse.getError())
                .map(SendDispatchResponse.ErrorInfo::getMsg)
                .orElse("Error desconocido al enviar comprobante")));
  }

  /**
   * Método funcional para validar y procesar una respuesta de consulta CDR.
   * 
   * @param cdrResponse Respuesta de la consulta CDR
   * @param onSuccess   Función a ejecutar si la consulta es exitosa
   * @param onError     Función a ejecutar si hay error
   * @return Resultado del procesamiento
   */
  default <T> T procesarCdr(
      FectchCdrResponse cdrResponse,
      Function<FectchCdrResponse, T> onSuccess,
      Function<String, T> onError) {
    return Optional.of(cdrResponse)
        .filter(response -> "0".equals(response.getCodRespuesta()))
        .map(onSuccess)
        .orElseGet(() -> onError.apply(
            Optional.ofNullable(cdrResponse.getError())
                .map(FectchCdrResponse.ErrorInfo::getDesError)
                .orElseGet(() -> Optional.ofNullable(cdrResponse.getError500())
                    .map(FectchCdrResponse.ErrorInfo500::getMsg)
                    .orElse("Error desconocido al consultar CDR"))));
  }
}
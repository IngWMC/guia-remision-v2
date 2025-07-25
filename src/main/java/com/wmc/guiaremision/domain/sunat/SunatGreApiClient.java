package com.wmc.guiaremision.domain.sunat;

import com.wmc.guiaremision.domain.service.sunat.dto.gre.*;
import com.wmc.guiaremision.domain.sunat.dto.gre.*;

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
public interface SunatGreApiClient {

  /**
   * Obtiene un token de autenticación de SUNAT.
   * 
   * @param request DTO con las credenciales para obtener el token
   * @return TokenResponse con la respuesta del token
   * @throws RuntimeException si hay error en la comunicación con SUNAT
   */
  TokenResponse obtenerToken(TokenRequest request);

  /**
   * Envía un comprobante (GRE) a SUNAT.
   * 
   * @param request      DTO con la información del archivo a enviar
   * @param accessToken  Token de autenticación obtenido previamente
   * @param numRucEmisor RUC del contribuyente emisor (11 dígitos)
   * @param codCpe       Código del comprobante (09: GRE Remitente, 31: GRE
   *                     Transportista)
   * @param numSerie     Número de serie del comprobante (T### para 09, V### para
   *                     31)
   * @param numCpe       Número del comprobante (1-8 dígitos)
   * @return EnviarComprobanteResponse con la respuesta del envío
   * @throws RuntimeException si hay error en la comunicación con SUNAT
   */
  EnviarComprobanteResponse enviarComprobante(
      EnviarComprobanteRequest request,
      String accessToken,
      String numRucEmisor,
      String codCpe,
      String numSerie,
      String numCpe);

  /**
   * Consulta el estado de un comprobante enviado a SUNAT.
   * 
   * @param numTicket   Número de ticket obtenido del método enviarComprobante
   * @param accessToken Token de autenticación obtenido previamente
   * @return ConsultarComprobanteResponse con la respuesta de la consulta
   * @throws RuntimeException si hay error en la comunicación con SUNAT
   */
  ConsultarComprobanteResponse consultarComprobante(
      String numTicket,
      String accessToken);

  /**
   * Método de conveniencia para enviar una GRE completa en un solo flujo.
   * Obtiene token, envía comprobante y consulta el estado usando programación
   * funcional.
   * 
   * @param request      DTO con la información del archivo a enviar
   * @param tokenRequest DTO con las credenciales para obtener el token
   * @param numRucEmisor RUC del contribuyente emisor
   * @param codCpe       Código del comprobante
   * @param numSerie     Número de serie del comprobante
   * @param numCpe       Número del comprobante
   * @return GreProcessResult con el resultado completo del proceso
   */
  default GreProcessResult enviarGreCompleto(
      EnviarComprobanteRequest request,
      TokenRequest tokenRequest,
      String numRucEmisor,
      String codCpe,
      String numSerie,
      String numCpe) {
    // Programación funcional: encadenamiento de operaciones
    return Optional.of(tokenRequest)
        .map(this::obtenerToken)
        .filter(TokenResponse::isSuccess)
        .map(tokenResponse -> enviarComprobante(request, tokenResponse.getAccessToken(),
            numRucEmisor, codCpe, numSerie, numCpe))
        .filter(EnviarComprobanteResponse::isSuccess)
        .map(envioResponse -> {
          // Obtener nuevo token para consulta
          TokenResponse newToken = obtenerToken(tokenRequest);
          if (newToken.isSuccess()) {
            ConsultarComprobanteResponse consultaResponse = consultarComprobante(envioResponse.getNumTicket(),
                newToken.getAccessToken());
            return new GreProcessResult(envioResponse, consultaResponse);
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
        .map(TokenResponse::getAccessToken)
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
      EnviarComprobanteResponse envioResponse,
      Function<String, T> onSuccess,
      Function<String, T> onError) {
    return Optional.of(envioResponse)
        .filter(EnviarComprobanteResponse::isSuccess)
        .map(EnviarComprobanteResponse::getNumTicket)
        .map(onSuccess)
        .orElseGet(() -> onError.apply(
            Optional.ofNullable(envioResponse.getError())
                .map(EnviarComprobanteResponse.ErrorInfo::getMsg)
                .orElse("Error desconocido al enviar comprobante")));
  }

  /**
   * DTO para el resultado completo del proceso de envío de GRE
   */
  class GreProcessResult {
    private final EnviarComprobanteResponse envioResponse;
    private final ConsultarComprobanteResponse consultaResponse;

    public GreProcessResult(EnviarComprobanteResponse envioResponse,
        ConsultarComprobanteResponse consultaResponse) {
      this.envioResponse = envioResponse;
      this.consultaResponse = consultaResponse;
    }

    public EnviarComprobanteResponse getEnvioResponse() {
      return envioResponse;
    }

    public ConsultarComprobanteResponse getConsultaResponse() {
      return consultaResponse;
    }

    public boolean isSuccess() {
      return envioResponse.isSuccess() && consultaResponse.isSuccess();
    }

    /**
     * Método funcional para procesar el resultado según el estado
     */
    public <T> T procesarResultado(
        Function<GreProcessResult, T> onSuccess,
        Function<String, T> onError) {
      return Optional.of(this)
          .filter(GreProcessResult::isSuccess)
          .map(onSuccess)
          .orElseGet(() -> onError.apply("Error en el proceso de GRE"));
    }
  }
}
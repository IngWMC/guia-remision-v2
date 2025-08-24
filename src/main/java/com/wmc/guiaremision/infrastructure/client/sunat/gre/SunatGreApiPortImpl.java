package com.wmc.guiaremision.infrastructure.client.sunat.gre;

import static com.wmc.guiaremision.infrastructure.common.Constant.AMPERSAND;

import com.wmc.guiaremision.domain.spi.sunat.SunatGreApiPort;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FectchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenResponse;
import com.wmc.guiaremision.infrastructure.common.Util;
import com.wmc.guiaremision.infrastructure.config.property.ApiGreProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Implementación del cliente de la API REST de SUNAT para Guías de Remisión
 * Electrónica.
 * Utiliza programación funcional sin CompletableFuture.
 * 
 * @author WMC
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SunatGreApiPortImpl implements SunatGreApiPort {

  private final RestTemplate restTemplate;
  private final ApiGreProperty apiGreProperty;

  @Override
  public TokenResponse getToken(TokenRequest request) {
    try {
      String url = apiGreProperty.getBeta().getTokenUrl()
          .replace("{client_id}", apiGreProperty.getBeta().getClientId());

      // Crear headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      // Crear body como form-urlencoded
      String body = this.buildBodyForToken(request);

      HttpEntity<String> entity = new HttpEntity<>(body, headers);

      log.debug("Enviando solicitud de token a: {}", url);

      ResponseEntity<TokenResponse> response = this.restTemplate.exchange(
          url,
          HttpMethod.POST,
          entity,
          TokenResponse.class);

      return procesarRespuestaToken(response);

    } catch (HttpClientErrorException e) {
      log.error("Error HTTP 4xx al obtener token: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorToken(e);
    } catch (HttpServerErrorException e) {
      log.error("Error HTTP 5xx al obtener token: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorToken(e);
    } catch (Exception e) {
      log.error("Error inesperado al obtener token: {}", e.getMessage(), e);
      return TokenResponse.builder()
          .success(false)
          .errorMessage("Error inesperado: " + e.getMessage())
          .build();
    }
  }

  /**
   * Construye el body para la solicitud de token en formato form-urlencoded.
   *
   * @param request Solicitud de token
   * @return Body codificado para la petición
   */
  private String buildBodyForToken(TokenRequest request) {
    return String.join(AMPERSAND,
        "grant_type=" + Util.encodeUrl(request.getGrantType()),
        "scope=" + Util.encodeUrl(apiGreProperty.getBaseUrl()),
        "client_id=" + Util.encodeUrl(apiGreProperty.getBeta().getClientId()),
        "client_secret=" + Util.encodeUrl(apiGreProperty.getBeta().getClientSecret()),
        "username=" + Util.encodeUrl(request.getUsername()),
        "password=" + Util.encodeUrl(request.getPassword()));
  }

  @Override
  public SendDispatchResponse sendDispatch(SendDispatchRequest request) {
    try {
      String url = apiGreProperty.getBeta().getSendUrl()
          .replace("{numRucEmisor}", request.getNumRucEmisor())
          .replace("{codCpe}", request.getCodCpe())
          .replace("{numSerie}", request.getNumSerie())
          .replace("{numCpe}", request.getNumCpe());

      // Crear headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(request.getAccessToken());

      HttpEntity<SendDispatchRequest> entity = new HttpEntity<>(request, headers);

      log.debug("Enviando comprobante a: {}", url);

      ResponseEntity<SendDispatchResponse> response = restTemplate.exchange(
          url,
          HttpMethod.POST,
          entity,
          SendDispatchResponse.class);

      return procesarRespuestaEnvio(response);

    } catch (HttpClientErrorException e) {
      log.error("Error HTTP 4xx al enviar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorEnvio(e);
    } catch (HttpServerErrorException e) {
      log.error("Error HTTP 5xx al enviar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorEnvio(e);
    } catch (Exception e) {
      log.error("Error inesperado al enviar comprobante: {}", e.getMessage(), e);
      return SendDispatchResponse.builder()
          .success(false)
          .build();
    }
  }

  @Override
  public FectchCdrResponse fetchCdr(String numTicket, String accessToken) {
    try {
      String url = apiGreProperty.getBeta().getTicketUrl()
          .replace("{numTicket}", numTicket);

      // Crear headers
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);

      HttpEntity<Void> entity = new HttpEntity<>(headers);

      log.debug("Consultando comprobante en: {}", url);

      ResponseEntity<FectchCdrResponse> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          FectchCdrResponse.class);

      return procesarRespuestaConsulta(response);

    } catch (HttpClientErrorException e) {
      log.error("Error HTTP 4xx al consultar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorConsulta(e);
    } catch (HttpServerErrorException e) {
      log.error("Error HTTP 5xx al consultar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorConsulta(e);
    } catch (Exception e) {
      log.error("Error inesperado al consultar comprobante: {}", e.getMessage(), e);
      return FectchCdrResponse.builder()
          .success(false)
          .build();
    }
  }

  /**
   * Procesa la respuesta de token de forma funcional.
   *
   * @param response Respuesta HTTP del token
   * @return TokenResponse procesado
   */
  private TokenResponse procesarRespuestaToken(ResponseEntity<TokenResponse> response) {
    return Optional.ofNullable(response.getBody())
        .filter(tokenResponse -> tokenResponse.getAccess_token() != null)
        .map(tokenResponse -> {
          log.info("Token obtenido exitosamente. Expira en {} segundos", tokenResponse.getExpires_in());
          return tokenResponse;
        })
        .orElseGet(() -> {
          log.error("Respuesta de token vacía o inválida");
          return TokenResponse.builder()
              .success(false)
              .errorMessage("Respuesta de token vacía o inválida")
              .build();
        });
  }

  /**
   * Procesa la respuesta de envío de forma funcional.
   *
   * @param response Respuesta HTTP del envío
   * @return SendDispatchResponse procesado
   */
  private SendDispatchResponse procesarRespuestaEnvio(ResponseEntity<SendDispatchResponse> response) {
    return Optional.ofNullable(response.getBody())
        .filter(envioResponse -> envioResponse.getNumTicket() != null)
        .map(envioResponse -> {
          log.info("Comprobante enviado exitosamente. Ticket: {}", envioResponse.getNumTicket());
          return envioResponse;
        })
        .orElseGet(() -> {
          log.error("Respuesta de envío vacía o inválida");
          return SendDispatchResponse.builder()
              .success(false)
              .build();
        });
  }

  /**
   * Procesa la respuesta de consulta de forma funcional.
   *
   * @param response Respuesta HTTP de la consulta
   * @return FectchCdrResponse procesado
   */
  private FectchCdrResponse procesarRespuestaConsulta(
      ResponseEntity<FectchCdrResponse> response) {
    return Optional.ofNullable(response.getBody())
        .map(consultaResponse -> {
          log.info("Consulta exitosa. Código respuesta: {}", consultaResponse.getCodRespuesta());
          return consultaResponse;
        })
        .orElseGet(() -> {
          log.error("Respuesta de consulta vacía");
          return FectchCdrResponse.builder()
              .success(false)
              .build();
        });
  }

  /**
   * Maneja errores de token de forma funcional.
   */
  private TokenResponse manejarErrorToken(Exception e) {
    return TokenResponse.builder()
        .success(false)
        .errorMessage(String.format("Error HTTP: %s", e.getMessage()))
        .build();
  }

  /**
   * Maneja errores de envío de forma funcional.
   */
  private SendDispatchResponse manejarErrorEnvio(Exception e) {
    return Optional.of(e)
        .filter(ex -> ex instanceof HttpServerErrorException)
        .map(ex -> (HttpServerErrorException) ex)
        .filter(ex -> ex.getStatusCode().value() == 500)
        .map(ex -> SendDispatchResponse.builder()
            .success(false)
            .error(SendDispatchResponse.ErrorInfo.builder()
                .cod("500")
                .msg("Error interno del servidor SUNAT")
                .exc(ex.getMessage())
                .build())
            .build())
        .orElseGet(() -> Optional.of(e)
            .filter(ex -> ex instanceof HttpClientErrorException)
            .map(ex -> (HttpClientErrorException) ex)
            .filter(ex -> ex.getStatusCode().value() == 422)
            .map(ex -> SendDispatchResponse.builder()
                .success(false)
                .validationError(SendDispatchResponse.ValidationErrorResponse.builder()
                    .cod("422")
                    .msg("Error de validación")
                    .exc(ex.getMessage())
                    .build())
                .build())
            .orElse(SendDispatchResponse.builder()
                .success(false)
                .build()));
  }

  /**
   * Maneja errores de consulta de forma funcional.
   */
  private FectchCdrResponse manejarErrorConsulta(Exception e) {
    return Optional.of(e)
        .filter(ex -> ex instanceof HttpServerErrorException)
        .map(ex -> (HttpServerErrorException) ex)
        .filter(ex -> ex.getStatusCode().value() == 500)
        .map(ex -> FectchCdrResponse.builder()
            .success(false)
            .error500(FectchCdrResponse.ErrorInfo500.builder()
                .cod("500")
                .msg("Error interno del servidor SUNAT")
                .exc(ex.getMessage())
                .build())
            .build())
        .orElseGet(() -> Optional.of(e)
            .filter(ex -> ex instanceof HttpClientErrorException)
            .map(ex -> (HttpClientErrorException) ex)
            .filter(ex -> ex.getStatusCode().value() == 422)
            .map(ex -> FectchCdrResponse.builder()
                .success(false)
                .validationError(FectchCdrResponse.ValidationErrorResponse.builder()
                    .cod("422")
                    .msg("Error de validación")
                    .exc(ex.getMessage())
                    .build())
                .build())
            .orElse(FectchCdrResponse.builder()
                .success(false)
                .build()));
  }
}
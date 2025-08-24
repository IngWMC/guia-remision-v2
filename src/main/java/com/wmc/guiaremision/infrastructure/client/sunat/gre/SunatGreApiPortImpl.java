package com.wmc.guiaremision.infrastructure.client.sunat.gre;

import com.wmc.guiaremision.domain.spi.sunat.SunatGreApiPort;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.FectchCdrResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.SendDispatchResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenResponse;
import com.wmc.guiaremision.infrastructure.config.property.ApiGreProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
      /*MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("grant_type", request.getGrantType());
      body.add("scope", apiGreProperty.getBaseUrl());
      body.add("client_id", apiGreProperty.getBeta().getClientId()); // request.getClientId()
      body.add("client_secret", apiGreProperty.getBeta().getClientSecret()); // request.getClientSecret()
      body.add("username", request.getUsername());
      body.add("password", request.getPassword());*/
      String body = this.toUrlEncoded(request);

      //HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
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

  private String toUrlEncoded(TokenRequest request) throws UnsupportedEncodingException {
    return "grant_type=" + URLEncoder.encode(request.getGrantType(), "UTF-8") + "&" +
        "scope=" + URLEncoder.encode(apiGreProperty.getBaseUrl(), "UTF-8") + "&" +
        "client_id=" + URLEncoder.encode(apiGreProperty.getBeta().getClientId(), "UTF-8") + "&" +
        "client_secret=" + URLEncoder.encode(apiGreProperty.getBeta().getClientSecret(), "UTF-8") + "&" +
        "username=" + URLEncoder.encode(request.getUsername(), "UTF-8") + "&" +
        "password=" + URLEncoder.encode(request.getPassword(), "UTF-8");
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
   * Método funcional para procesar respuesta de token
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
   * Método funcional para procesar respuesta de envío
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
   * Método funcional para procesar respuesta de consulta
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
   * Método funcional para manejar errores de token
   */
  private TokenResponse manejarErrorToken(Exception e) {
    return TokenResponse.builder()
        .success(false)
        .errorMessage(String.format("Error HTTP: %s", e.getMessage()))
        .build();
  }

  /**
   * Método funcional para manejar errores de envío
   */
  private SendDispatchResponse manejarErrorEnvio(Exception e) {
    if (e instanceof HttpServerErrorException && ((HttpServerErrorException) e).getStatusCode().value() == 500) {
      return SendDispatchResponse.builder()
          .success(false)
          .error(SendDispatchResponse.ErrorInfo.builder()
              .cod("500")
              .msg("Error interno del servidor SUNAT")
              .exc(e.getMessage())
              .build())
          .build();
    } else if (e instanceof HttpClientErrorException && ((HttpClientErrorException) e).getStatusCode().value() == 422) {
      return SendDispatchResponse.builder()
          .success(false)
          .validationError(SendDispatchResponse.ValidationErrorResponse.builder()
              .cod("422")
              .msg("Error de validación")
              .exc(e.getMessage())
              .build())
          .build();
    } else {
      return SendDispatchResponse.builder()
          .success(false)
          .build();
    }
  }

  /**
   * Método funcional para manejar errores de consulta
   */
  private FectchCdrResponse manejarErrorConsulta(Exception e) {
    if (e instanceof HttpServerErrorException && ((HttpServerErrorException) e).getStatusCode().value() == 500) {
      return FectchCdrResponse.builder()
          .success(false)
          .error500(FectchCdrResponse.ErrorInfo500.builder()
              .cod("500")
              .msg("Error interno del servidor SUNAT")
              .exc(e.getMessage())
              .build())
          .build();
    } else if (e instanceof HttpClientErrorException && ((HttpClientErrorException) e).getStatusCode().value() == 422) {
      return FectchCdrResponse.builder()
          .success(false)
          .validationError(FectchCdrResponse.ValidationErrorResponse.builder()
              .cod("422")
              .msg("Error de validación")
              .exc(e.getMessage())
              .build())
          .build();
    } else {
      return FectchCdrResponse.builder()
          .success(false)
          .build();
    }
  }
}
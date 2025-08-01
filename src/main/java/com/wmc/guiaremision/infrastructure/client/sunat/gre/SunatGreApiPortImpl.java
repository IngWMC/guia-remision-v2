package com.wmc.guiaremision.infrastructure.client.sunat.gre;

import com.wmc.guiaremision.domain.spi.sunat.SunatGreApiPort;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.ConsultarComprobanteResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.EnviarComprobanteRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.EnviarComprobanteResponse;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenRequest;
import com.wmc.guiaremision.domain.spi.sunat.dto.gre.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
@Service
@RequiredArgsConstructor
public class SunatGreApiPortImpl implements SunatGreApiPort {

  private final RestTemplate restTemplate;

  @Value("$sunat.api.beta.token-url")
  private String tockenBaseUrl;

  @Value("$sunat.api.beta.send-url")
  private String sendBaseUrl;

  @Value("$sunat.api.beta.ticket-url")
  private String ticketBaseUrl;

  @Value("${sunat.api.beta.client-id}")
  private String clientId;

  @Value("${sunat.api.beta.client-secret}")
  private String clientSecret;

  private final String username = "";
  private final String password = "";

  @Override
  public TokenResponse obtenerToken(TokenRequest request) {
    try {
      log.info("Obteniendo token de SUNAT para cliente: {}", request.getClientId());

      String url = String.format("%s/%s/oauth2/token/", tockenBaseUrl, request.getClientId());

      // Crear headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      // Crear body como form-urlencoded
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("grant_type", request.getGrantType());
      body.add("scope", request.getScope());
      body.add("client_id", request.getClientId());
      body.add("client_secret", request.getClientSecret());
      body.add("username", request.getUsername());
      body.add("password", request.getPassword());

      HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

      log.debug("Enviando solicitud de token a: {}", url);

      ResponseEntity<TokenResponse> response = restTemplate.exchange(
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

  @Override
  public EnviarComprobanteResponse enviarComprobante(
      EnviarComprobanteRequest request,
      String accessToken,
      String numRucEmisor,
      String codCpe,
      String numSerie,
      String numCpe) {
    try {
      log.info("Enviando comprobante GRE: {}-{}-{}-{}", numRucEmisor, codCpe, numSerie, numCpe);

      String url = String.format("%s/%s-%s-%s-%s", sendBaseUrl, numRucEmisor, codCpe, numSerie, numCpe);

      // Crear headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(accessToken);

      HttpEntity<EnviarComprobanteRequest> entity = new HttpEntity<>(request, headers);

      log.debug("Enviando comprobante a: {}", url);

      ResponseEntity<EnviarComprobanteResponse> response = restTemplate.exchange(
          url,
          HttpMethod.POST,
          entity,
          EnviarComprobanteResponse.class);

      return procesarRespuestaEnvio(response);

    } catch (HttpClientErrorException e) {
      log.error("Error HTTP 4xx al enviar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorEnvio(e);
    } catch (HttpServerErrorException e) {
      log.error("Error HTTP 5xx al enviar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorEnvio(e);
    } catch (Exception e) {
      log.error("Error inesperado al enviar comprobante: {}", e.getMessage(), e);
      return EnviarComprobanteResponse.builder()
          .success(false)
          .build();
    }
  }

  @Override
  public ConsultarComprobanteResponse consultarComprobante(String numTicket, String accessToken) {
    try {
      log.info("Consultando estado del comprobante con ticket: {}", numTicket);

      String url = String.format("%s/envios/%s", ticketBaseUrl, numTicket);

      // Crear headers
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken);

      HttpEntity<Void> entity = new HttpEntity<>(headers);

      log.debug("Consultando comprobante en: {}", url);

      ResponseEntity<ConsultarComprobanteResponse> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          ConsultarComprobanteResponse.class);

      return procesarRespuestaConsulta(response);

    } catch (HttpClientErrorException e) {
      log.error("Error HTTP 4xx al consultar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorConsulta(e);
    } catch (HttpServerErrorException e) {
      log.error("Error HTTP 5xx al consultar comprobante: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
      return manejarErrorConsulta(e);
    } catch (Exception e) {
      log.error("Error inesperado al consultar comprobante: {}", e.getMessage(), e);
      return ConsultarComprobanteResponse.builder()
          .success(false)
          .build();
    }
  }

  /**
   * Método funcional para procesar respuesta de token
   */
  private TokenResponse procesarRespuestaToken(ResponseEntity<TokenResponse> response) {
    return Optional.ofNullable(response.getBody())
        .filter(tokenResponse -> tokenResponse.getAccessToken() != null)
        .map(tokenResponse -> {
          log.info("Token obtenido exitosamente. Expira en {} segundos", tokenResponse.getExpiresIn());
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
  private EnviarComprobanteResponse procesarRespuestaEnvio(ResponseEntity<EnviarComprobanteResponse> response) {
    return Optional.ofNullable(response.getBody())
        .filter(envioResponse -> envioResponse.getNumTicket() != null)
        .map(envioResponse -> {
          log.info("Comprobante enviado exitosamente. Ticket: {}", envioResponse.getNumTicket());
          return envioResponse;
        })
        .orElseGet(() -> {
          log.error("Respuesta de envío vacía o inválida");
          return EnviarComprobanteResponse.builder()
              .success(false)
              .build();
        });
  }

  /**
   * Método funcional para procesar respuesta de consulta
   */
  private ConsultarComprobanteResponse procesarRespuestaConsulta(
      ResponseEntity<ConsultarComprobanteResponse> response) {
    return Optional.ofNullable(response.getBody())
        .map(consultaResponse -> {
          log.info("Consulta exitosa. Código respuesta: {}", consultaResponse.getCodRespuesta());
          return consultaResponse;
        })
        .orElseGet(() -> {
          log.error("Respuesta de consulta vacía");
          return ConsultarComprobanteResponse.builder()
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
  private EnviarComprobanteResponse manejarErrorEnvio(Exception e) {
    if (e instanceof HttpServerErrorException && ((HttpServerErrorException) e).getStatusCode().value() == 500) {
      return EnviarComprobanteResponse.builder()
          .success(false)
          .error(EnviarComprobanteResponse.ErrorInfo.builder()
              .cod("500")
              .msg("Error interno del servidor SUNAT")
              .exc(e.getMessage())
              .build())
          .build();
    } else if (e instanceof HttpClientErrorException && ((HttpClientErrorException) e).getStatusCode().value() == 422) {
      return EnviarComprobanteResponse.builder()
          .success(false)
          .validationError(EnviarComprobanteResponse.ValidationErrorResponse.builder()
              .cod("422")
              .msg("Error de validación")
              .exc(e.getMessage())
              .build())
          .build();
    } else {
      return EnviarComprobanteResponse.builder()
          .success(false)
          .build();
    }
  }

  /**
   * Método funcional para manejar errores de consulta
   */
  private ConsultarComprobanteResponse manejarErrorConsulta(Exception e) {
    if (e instanceof HttpServerErrorException && ((HttpServerErrorException) e).getStatusCode().value() == 500) {
      return ConsultarComprobanteResponse.builder()
          .success(false)
          .error500(ConsultarComprobanteResponse.ErrorInfo500.builder()
              .cod("500")
              .msg("Error interno del servidor SUNAT")
              .exc(e.getMessage())
              .build())
          .build();
    } else if (e instanceof HttpClientErrorException && ((HttpClientErrorException) e).getStatusCode().value() == 422) {
      return ConsultarComprobanteResponse.builder()
          .success(false)
          .validationError(ConsultarComprobanteResponse.ValidationErrorResponse.builder()
              .cod("422")
              .msg("Error de validación")
              .exc(e.getMessage())
              .build())
          .build();
    } else {
      return ConsultarComprobanteResponse.builder()
          .success(false)
          .build();
    }
  }

  /**
   * Método de conveniencia para crear un TokenRequest con las credenciales
   * configuradas
   */
  public TokenRequest createDefaultTokenRequest() {
    return TokenRequest.builder()
        .clientId(clientId)
        .clientSecret(clientSecret)
        .username(username)
        .password(password)
        .build();
  }
}
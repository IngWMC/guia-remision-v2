package com.wmc.guiaremision.infrastructure.rest.example;

import com.wmc.guiaremision.infrastructure.rest.SunatGreApiClient;
import com.wmc.guiaremision.infrastructure.rest.dto.sunat.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.function.Function;

/**
 * Ejemplo de uso del cliente de la API REST de SUNAT para Guías de Remisión
 * Electrónica.
 * Demuestra el uso de programación funcional sin CompletableFuture.
 * 
 * @author WMC
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SunatGreApiClientExample {

  private final SunatGreApiClient sunatGreApiClient;

  /**
   * Ejemplo 1: Uso básico de los métodos individuales
   */
  public void ejemploUsoBasico() {
    log.info("=== Ejemplo 1: Uso básico de métodos individuales ===");

    // 1. Obtener token
    TokenRequest tokenRequest = TokenRequest.builder()
        .clientId("tu-client-id")
        .clientSecret("tu-client-secret")
        .username("12345678901USUARIO")
        .password("tu-password")
        .build();

    TokenResponse tokenResponse = sunatGreApiClient.obtenerToken(tokenRequest);

    // Usar programación funcional para procesar el token
    String resultado = sunatGreApiClient.procesarToken(
        tokenResponse,
        accessToken -> {
          log.info("Token obtenido exitosamente: {}", accessToken);
          return "SUCCESS";
        },
        errorMessage -> {
          log.error("Error al obtener token: {}", errorMessage);
          return "ERROR";
        });

    log.info("Resultado del procesamiento: {}", resultado);
  }

  /**
   * Ejemplo 2: Envío de comprobante con programación funcional
   */
  public void ejemploEnvioComprobante() {
    log.info("=== Ejemplo 2: Envío de comprobante ===");

    // Crear request de envío
    EnviarComprobanteRequest request = EnviarComprobanteRequest.builder()
        .archivo(EnviarComprobanteRequest.Archivo.builder()
            .nomArchivo("12345678901-09-T001-00000001.zip")
            .arcGreZip(Base64.getEncoder().encodeToString("contenido-zip".getBytes()))
            .hashZip("hash-sha256-del-archivo")
            .build())
        .build();

    // Obtener token
    TokenRequest tokenRequest = crearTokenRequest();
    TokenResponse tokenResponse = sunatGreApiClient.obtenerToken(tokenRequest);

    if (tokenResponse.isSuccess()) {
      // Enviar comprobante
      EnviarComprobanteResponse envioResponse = sunatGreApiClient.enviarComprobante(
          request,
          tokenResponse.getAccessToken(),
          "12345678901", // RUC
          "09", // Código GRE Remitente
          "T001", // Serie
          "00000001" // Número
      );

      // Usar programación funcional para procesar el envío
      String resultado = sunatGreApiClient.procesarEnvio(
          envioResponse,
          ticket -> {
            log.info("Comprobante enviado exitosamente. Ticket: {}", ticket);
            return "ENVIADO";
          },
          errorMessage -> {
            log.error("Error al enviar comprobante: {}", errorMessage);
            return "ERROR_ENVIO";
          });

      log.info("Resultado del envío: {}", resultado);
    }
  }

  /**
   * Ejemplo 3: Consulta de comprobante
   */
  public void ejemploConsultaComprobante() {
    log.info("=== Ejemplo 3: Consulta de comprobante ===");

    String numTicket = "ticket-ejemplo-uuid";

    // Obtener token
    TokenRequest tokenRequest = crearTokenRequest();
    TokenResponse tokenResponse = sunatGreApiClient.obtenerToken(tokenRequest);

    if (tokenResponse.isSuccess()) {
      // Consultar estado
      ConsultarComprobanteResponse consultaResponse = sunatGreApiClient.consultarComprobante(
          numTicket,
          tokenResponse.getAccessToken());

      // Procesar respuesta usando programación funcional
      procesarRespuestaConsulta(consultaResponse);
    }
  }

  /**
   * Ejemplo 4: Uso del método completo (recomendado)
   */
  public void ejemploUsoCompleto() {
    log.info("=== Ejemplo 4: Uso del método completo ===");

    // Crear request de envío
    EnviarComprobanteRequest request = EnviarComprobanteRequest.builder()
        .archivo(EnviarComprobanteRequest.Archivo.builder()
            .nomArchivo("12345678901-09-T001-00000002.zip")
            .arcGreZip(Base64.getEncoder().encodeToString("contenido-zip".getBytes()))
            .hashZip("hash-sha256-del-archivo")
            .build())
        .build();

    // Crear request de token
    TokenRequest tokenRequest = crearTokenRequest();

    try {
      // Ejecutar proceso completo usando programación funcional
      SunatGreApiClient.GreProcessResult result = sunatGreApiClient.enviarGreCompleto(
          request,
          tokenRequest,
          "12345678901", // RUC
          "09", // Código GRE Remitente
          "T001", // Serie
          "00000002" // Número
      );

      // Procesar resultado usando programación funcional
      String resultado = result.procesarResultado(
          successResult -> {
            log.info("GRE procesada exitosamente");
            log.info("Ticket: {}", successResult.getEnvioResponse().getNumTicket());
            log.info("Código respuesta: {}", successResult.getConsultaResponse().getCodRespuesta());
            return "PROCESO_EXITOSO";
          },
          errorMessage -> {
            log.error("Error en el proceso: {}", errorMessage);
            return "PROCESO_ERROR";
          });

      log.info("Resultado final: {}", resultado);

    } catch (Exception e) {
      log.error("Error en el proceso completo: {}", e.getMessage(), e);
    }
  }

  /**
   * Ejemplo 5: Manejo de errores con programación funcional
   */
  public void ejemploManejoErrores() {
    log.info("=== Ejemplo 5: Manejo de errores ===");

    // Simular error con credenciales inválidas
    TokenRequest tokenRequestInvalido = TokenRequest.builder()
        .clientId("client-invalido")
        .clientSecret("secret-invalido")
        .username("usuario-invalido")
        .password("password-invalido")
        .build();

    TokenResponse tokenResponse = sunatGreApiClient.obtenerToken(tokenRequestInvalido);

    // Usar programación funcional para manejar el error
    String resultado = sunatGreApiClient.procesarToken(
        tokenResponse,
        accessToken -> {
          log.info("Token válido obtenido");
          return "TOKEN_VALIDO";
        },
        errorMessage -> {
          log.warn("Token inválido: {}", errorMessage);
          // Aquí podrías implementar lógica de reintento, notificación, etc.
          return "TOKEN_INVALIDO";
        });

    log.info("Resultado del manejo de errores: {}", resultado);
  }

  /**
   * Ejemplo 6: Composición de operaciones con programación funcional
   */
  public void ejemploComposicionOperaciones() {
    log.info("=== Ejemplo 6: Composición de operaciones ===");

    // Función para validar RUC
    Function<String, Boolean> validarRuc = ruc -> ruc != null && ruc.length() == 11 && ruc.matches("\\d+");

    // Función para validar serie
    Function<String, Boolean> validarSerie = serie -> serie != null && serie.matches("T\\d{3}");

    // Función para validar número
    Function<String, Boolean> validarNumero = numero -> numero != null && numero.matches("\\d{1,8}");

    // Composición de validaciones
    String ruc = "12345678901";
    String serie = "T001";
    String numero = "00000001";

    boolean esValido = validarRuc.apply(ruc) &&
        validarSerie.apply(serie) &&
        validarNumero.apply(numero);

    if (esValido) {
      log.info("Datos válidos, procediendo con el envío");
      // Aquí iría la lógica de envío
    } else {
      log.error("Datos inválidos para el envío");
    }
  }

  /**
   * Método auxiliar para crear un TokenRequest con datos de ejemplo
   */
  private TokenRequest crearTokenRequest() {
    return TokenRequest.builder()
        .clientId("tu-client-id")
        .clientSecret("tu-client-secret")
        .username("12345678901USUARIO")
        .password("tu-password")
        .build();
  }

  /**
   * Método auxiliar para procesar respuesta de consulta
   */
  private void procesarRespuestaConsulta(ConsultarComprobanteResponse consultaResponse) {
    if (consultaResponse.isSuccess()) {
      switch (consultaResponse.getCodRespuesta()) {
        case "0":
          log.info("GRE aceptada por SUNAT");
          if ("1".equals(consultaResponse.getIndCdrGenerado())) {
            log.info("CDR generado: {}", consultaResponse.getArcCdr());
          }
          break;
        case "98":
          log.info("GRE en proceso de validación");
          break;
        case "99":
          log.error("GRE rechazada por SUNAT");
          if (consultaResponse.getError() != null) {
            log.error("Error: {} - {}",
                consultaResponse.getError().getNumError(),
                consultaResponse.getError().getDesError());
          }
          break;
        default:
          log.warn("Código de respuesta desconocido: {}", consultaResponse.getCodRespuesta());
      }
    } else {
      log.error("Error en la consulta");
    }
  }
}
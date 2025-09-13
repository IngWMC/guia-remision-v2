package com.wmc.guiaremision.infrastructure.web.exception;

import static com.wmc.guiaremision.infrastructure.common.Constant.DATE_FORMAT;
import static com.wmc.guiaremision.infrastructure.common.Constant.HOUR_FORMAT;
import static com.wmc.guiaremision.infrastructure.common.Constant.ZONE_ID;

import com.wmc.guiaremision.infrastructure.web.dto.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFound(NoHandlerFoundException ex) {
    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime()) // Fecha y hora del error
        .mensaje("El recurso solicitado no fue encontrado.") // Mensaje claro
        .detalle("Ruta no válida: " + ex.getRequestURL()) // Detalles adicionales
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ExceptionResponse> handleBadRequest(BadRequestException ex, WebRequest request) {
    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime()) // Fecha y hora del error
        .mensaje(ex.getMessage()) // Mensaje personalizado desde la excepción
        .detalle(request.getDescription(false)) // Detalles adicionales
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ExceptionResponse> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime()) // Fecha y hora del error
        .mensaje(ex.getMessage()) // Mensaje personalizado desde la excepción
        .detalle(request.getDescription(false)) // Detalles adicionales
        .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      // errors.put(error.getField(), error.getDefaultMessage());
      addErrorToMap(errors, error.getField(), error.getDefaultMessage());
    });

    ex.getBindingResult().getGlobalErrors().forEach(error -> {
      errors.put(error.getObjectName(), error.getDefaultMessage());
    });

    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime()) // Fecha y hora del error
        .mensaje("Error de validación en los campos proporcionados.") // Mensaje general
        .detalle(errors) // Detalles de los errores
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, WebRequest request) {
    log.error("Error: {} - {}", ex.getMessage(), ex.getStackTrace());

    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime()) // Fecha y hora del error
        .mensaje("Ocurrió un error inesperado.") // Mensaje genérico
        .detalle(request.getDescription(false)) // Detalles adicionales de la solicitud
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  private String getCurrentDateTime() {
    ZonedDateTime nowInPeru = ZonedDateTime.now(ZoneId.of(ZONE_ID));
    return nowInPeru.format(DateTimeFormatter.ofPattern(DATE_FORMAT
        .concat(" ")
        .concat(HOUR_FORMAT)));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException ex) {
    String mensaje = "Error de autenticación";
    String detalle = "Token JWT inválido o expirado";

    // Detectar si es por token faltante
    if (ex.getMessage() != null && ex.getMessage().contains("Full authentication is required")) {
      mensaje = "Token JWT requerido";
      detalle = "Debe proporcionar un token JWT válido en el header Authorization";
    }

    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime())
        .mensaje(mensaje)
        .detalle(detalle)
        .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex) {
    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime())
        .mensaje("Credenciales inválidas")
        .detalle("Usuario o contraseña incorrectos")
        .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime())
        .mensaje("Acceso denegado")
        .detalle("No tiene permisos para acceder a este recurso")
        .build();

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  private void addErrorToMap(Map<String, Object> map, String fieldPath, String message) {
    String[] parts = fieldPath.split("\\.");
    Map<String, Object> current = map;
    for (int i = 0; i < parts.length - 1; i++) {
      String part = parts[i];
      if (!current.containsKey(part) || !(current.get(part) instanceof Map)) {
        current.put(part, new HashMap<String, Object>());
      }
      current = (Map<String, Object>) current.get(part);
    }
    current.put(parts[parts.length - 1], message);
  }
}

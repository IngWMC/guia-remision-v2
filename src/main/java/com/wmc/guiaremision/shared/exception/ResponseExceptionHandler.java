package com.wmc.guiaremision.shared.exception;

import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.shared.exception.custom.BadRequestException;
import com.wmc.guiaremision.infrastructure.web.dto.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura y responde a las excepciones más comunes lanzadas por los
 * controladores REST,
 * devolviendo una respuesta estructurada y amigable para el cliente.
 */
@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {
  /**
   * Maneja excepciones cuando no se encuentra un handler para la ruta solicitada.
   * 
   * @param ex excepción lanzada por Spring cuando no se encuentra la ruta
   * @return respuesta con estado 404 y detalles del error
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFound(NoHandlerFoundException ex) {
    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(Util.getCurrentDateTime())
        .mensaje("El recurso solicitado no fue encontrado.")
        .detalle("Ruta no válida: " + ex.getRequestURL())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  /**
   * Maneja excepciones personalizadas de tipo BadRequestException.
   * 
   * @param ex      excepción personalizada
   * @param request información de la solicitud
   * @return respuesta con estado 400 y detalles del error
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ExceptionResponse> handleBadRequest(BadRequestException ex, WebRequest request) {
    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(Util.getCurrentDateTime())
        .mensaje(ex.getMessage())
        .detalle(request.getDescription(false))
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * Maneja errores de validación de argumentos en los métodos de los
   * controladores.
   * 
   * @param ex excepción lanzada por validaciones fallidas
   * @return respuesta con estado 400 y detalles de los campos inválidos
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
    log.error("Error de validación de campos: {} - {}", ex.getMessage(), ex.getStackTrace());

    Map<String, Object> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      addErrorToMap(errors, error.getField(), error.getDefaultMessage());
    });

    ex.getBindingResult().getGlobalErrors().forEach(error -> {
      errors.put(error.getObjectName(), error.getDefaultMessage());
    });

    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(Util.getCurrentDateTime())
        .mensaje("Error de validación en los campos proporcionados.")
        .detalle(errors)
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * Maneja cualquier otra excepción no controlada de forma específica.
   * 
   * @param ex      excepción genérica
   * @param request información de la solicitud
   * @return respuesta con estado 500 y detalles del error
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, WebRequest request) {
    log.error("Error: {} - {}", ex.getMessage(), ex.getStackTrace());

    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(Util.getCurrentDateTime())
        .mensaje("Ocurrió un error inesperado.")
        .detalle(request.getDescription(false))
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  /**
   * Agrega un mensaje de error a un mapa anidado según la ruta del campo.
   * 
   * @param map       mapa de errores
   * @param fieldPath ruta del campo (puede ser anidada)
   * @param message   mensaje de error
   */
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

package com.wmc.guiaremision.infrastructure.web.exception;

import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.DATE_FORMAT;
import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.HOUR_FORMAT;
import static com.wmc.guiaremision.infrastructure.common.constant.FormatsConstant.ZONE_ID;

import com.wmc.guiaremision.infrastructure.web.dto.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
      // errors.put("\"" + error.getField() + "\"", "\"" + error.getDefaultMessage() + "\"");
    }

    ExceptionResponse response = ExceptionResponse.builder()
        .fecha(getCurrentDateTime()) // Fecha y hora del error
        .mensaje("Error de validación en los campos proporcionados.") // Mensaje general
        .detalle(errors) // Detalles de los errores
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, WebRequest request) {
    log.error("Error: {}", ex.getMessage());

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
        .concat( " ")
        .concat(HOUR_FORMAT)));
  }
}

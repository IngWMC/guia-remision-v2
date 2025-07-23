package com.wmc.guiaremision.infrastructure.rest.dto.sunat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para la respuesta del envío de comprobante a SUNAT.
 * 
 * @author WMC
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnviarComprobanteResponse {

  /**
   * Número de ticket (UUID) generado por el envío realizado.
   * UUID se expresa mediante 32 dígitos hexadecimales divididos en cinco grupos
   * separados por guiones de la forma 8-4-4-4-12
   */
  private String numTicket;

  /**
   * Fecha de recepción de envío del comprobante.
   * Formato: 'yyyy-mm-dd,'T','hh:ii:ss'
   */
  private LocalDateTime fecRecepcion;

  /**
   * Indica si la respuesta fue exitosa
   */
  @Builder.Default
  private boolean success = true;

  /**
   * Información de error en caso de fallo (código 500)
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ErrorInfo {
    private String cod;
    private String msg;
    private String exc;
  }

  /**
   * Información de error en caso de fallo (código 422)
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ValidationError {
    private String cod;
    private String msg;
  }

  /**
   * Error de tipo 500
   */
  private ErrorInfo error;

  /**
   * Error de tipo 422
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ValidationErrorResponse {
    private String cod;
    private String msg;
    private String exc;
    private List<ValidationError> errors;
  }

  /**
   * Respuesta de error de validación
   */
  private ValidationErrorResponse validationError;
}
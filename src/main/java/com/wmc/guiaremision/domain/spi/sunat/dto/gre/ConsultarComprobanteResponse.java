package com.wmc.guiaremision.domain.spi.sunat.dto.gre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para la respuesta de consulta de comprobante a SUNAT.
 * 
 * @author WMC
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultarComprobanteResponse {

  /**
   * Código de respuesta.
   * 98: en proceso, 99: envío con error, 0: envío OK
   */
  private String codRespuesta;

  /**
   * Información de error (solo se genera si codRespuesta es 99)
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ErrorInfo {
    /**
     * Número de error encontrado para el envío realizado
     */
    private String numError;

    /**
     * Detalle del error encontrado para el envío realizado
     */
    private String desError;
  }

  /**
   * Error solo se genera si codRespuesta es 99
   */
  private ErrorInfo error;

  /**
   * Base64 del CDR generado (Constancia de Recepción).
   * Solo se genera si codRespuesta es 0 o 99 e indCdrGenerado es 1
   */
  private String arcCdr;

  /**
   * Indicador de generación de CDR.
   * 1: Si genera CDR, 0: No genera CDR
   */
  private String indCdrGenerado;

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
  public static class ErrorInfo500 {
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
  private ErrorInfo500 error500;

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
package com.wmc.guiaremision.domain.spi.sunat.dto.gre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta del token de autenticación de SUNAT.
 * 
 * @author WMC
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

  /**
   * Token de acceso generado por SUNAT
   */
  private String access_token;

  /**
   * Tipo de token (ej: "Bearer")
   */
  private String token_type;

  /**
   * Tiempo de expiración del token en segundos
   */
  private Integer expires_in;

  /**
   * Indica si la respuesta fue exitosa
   */
  @Builder.Default
  private boolean success = true;

  /**
   * Mensaje de error en caso de fallo
   */
  private String errorMessage;
}
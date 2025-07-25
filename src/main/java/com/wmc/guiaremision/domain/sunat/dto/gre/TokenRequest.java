package com.wmc.guiaremision.domain.sunat.dto.gre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la solicitud de token de autenticación de SUNAT.
 * 
 * @author WMC
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {

  /**
   * Tipo de concesión de token (valor fijo: "password")
   */
  @Builder.Default
  private String grantType = "password";

  /**
   * Alcance del token (valor fijo: "https://api-cpe.sunat.gob.pe")
   */
  @Builder.Default
  private String scope = "https://api-cpe.sunat.gob.pe";

  /**
   * ID del cliente generado en el menú SOL
   */
  private String clientId;

  /**
   * Secreto del cliente generado en el menú SOL
   */
  private String clientSecret;

  /**
   * Usuario compuesto por: Número de RUC + Usuario SOL
   */
  private String username;

  /**
   * Contraseña del usuario SOL
   */
  private String password;
}
package com.wmc.guiaremision.infrastructure.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propiedades de configuración para el manejo de JWT.
 * <p>
 * Esta clase mapea las propiedades definidas en el archivo de configuración
 * bajo el prefijo 'jwt', permitiendo acceder a la clave secreta y el tiempo
 * de expiración del token JWT.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
  /**
   * Clave secreta utilizada para firmar y verificar los tokens JWT.
   */
  private String secret;

  /**
   * Tiempo de expiración del token JWT.
   * Se espera que esté definido en milisegundos.
   */
  private Long expiration;
}

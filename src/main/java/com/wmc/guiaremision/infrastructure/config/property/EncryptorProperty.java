package com.wmc.guiaremision.infrastructure.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propiedades de configuración para el cifrado de datos sensibles.
 * <p>
 * Lee la configuración desde application.yml bajo el prefijo
 * {@code security.encryptor}.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "security.encryptor")
public class EncryptorProperty {

  /**
   * Clave secreta para el cifrado AES.
   * Debe tener al menos 16 caracteres.
   */
  private String secretKey;

  /**
   * Salt en formato hexadecimal para mayor seguridad.
   * Debe tener exactamente 16 caracteres.
   */
  private String salt;
}

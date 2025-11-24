package com.wmc.guiaremision.domain.spi.security;

/**
 * Puerto SPI para cifrado simétrico reversible de datos sensibles.
 * <p>
 * Útil para datos que necesitan ser recuperados: contraseñas de certificados,
 * claves API, tokens. <strong>NO usar para contraseñas de usuarios</strong>
 * (usar BCrypt).
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
public interface EncryptorSecurity {

  /**
   * Cifra un texto usando AES-256.
   *
   * @param text texto a cifrar
   * @return texto cifrado en Base64
   * @throws IllegalArgumentException si text es null o vacío
   */
  String encrypt(String text);

  /**
   * Descifra un texto previamente cifrado.
   *
   * @param encryptedText texto cifrado en Base64
   * @return texto plano original
   * @throws IllegalArgumentException si encryptedText es null o vacío
   * @throws RuntimeException         si la clave es incorrecta o el formato
   *                                  inválido
   */
  String decrypt(String encryptedText);
}

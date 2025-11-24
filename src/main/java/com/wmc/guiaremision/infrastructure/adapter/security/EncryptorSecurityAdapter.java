package com.wmc.guiaremision.infrastructure.adapter.security;

import com.wmc.guiaremision.domain.spi.security.EncryptorSecurity;
import com.wmc.guiaremision.infrastructure.config.property.EncryptorProperty;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

/**
 * Adaptador de infraestructura para cifrado de datos sensibles usando AES-256.
 * <p>
 * Implementa {@link EncryptorSecurity} usando Spring Security Crypto.
 * Configuración a través de {@link EncryptorProperty}.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptorSecurityAdapter implements EncryptorSecurity {

  private final EncryptorProperty encryptorProperty;
  private TextEncryptor encryptor;

  /**
   * Inicializa el encriptador AES-256 con la configuración proporcionada.
   *
   * @throws IllegalArgumentException si la configuración es inválida
   */
  @PostConstruct
  public void init() {
    // Crear encriptador con AES-256
    this.encryptor = Encryptors.text(
        encryptorProperty.getSecretKey(),
        encryptorProperty.getSalt());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String encrypt(String text) {
    try {
      return encryptor.encrypt(text);
    } catch (Exception e) {
      log.error("Error al cifrar el texto: {}", e.getMessage());
      throw new RuntimeException("Error al cifrar lel texto", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String decrypt(String encryptedText) {
    try {
      return encryptor.decrypt(encryptedText);
    } catch (Exception e) {
      log.error("Error al descifrar el texto: {}", e.getMessage());
      throw new RuntimeException(
          "Error al descifrar el texto. Verifique la clave de cifrado.", e);
    }
  }
}
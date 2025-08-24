package com.wmc.guiaremision.infrastructure.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuración de la API GRE de SUNAT.
 * Mapea la sección 'sunat.api.gre' del application.yml
 *
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "sunat.api.gre")
public class ApiGreProperty {

  /**
   * URL base de la API GRE de SUNAT
   */
  private String baseUrl;

  /**
   * URL para obtener token de autenticación
   */
  private String tokenUrl;

  /**
   * URL para enviar comprobantes
   */
  private String sendUrl;

  /**
   * URL para consultar tickets
   */
  private String ticketUrl;

  /**
   * Configuración para ambiente beta/pruebas
   */
  private BetaConfig beta;

  /**
   * Configuración interna para ambiente beta
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BetaConfig {

    /**
     * URL base para ambiente beta
     */
    private String baseUrl;

    /**
     * URL para obtener token en ambiente beta
     */
    private String tokenUrl;

    /**
     * URL para enviar comprobantes en ambiente beta
     */
    private String sendUrl;

    /**
     * URL para consultar tickets en ambiente beta
     */
    private String ticketUrl;

    /**
     * ID del cliente para ambiente beta
     */
    private String clientId;

    /**
     * Secret del cliente para ambiente beta
     */
    private String clientSecret;
  }
}

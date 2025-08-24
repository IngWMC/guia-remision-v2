package com.wmc.guiaremision.domain.spi.sunat.dto.gre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la solicitud de envío de comprobante a SUNAT.
 * 
 * @author WMC
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendDispatchRequest {
  /**
   * Token de acceso para la autenticación con SUNAT.
   * Este token debe ser obtenido previamente mediante el flujo de autenticación.
   */
  private String accessToken;

  /**
   * Número de RUC del contribuyente emisor.
   * Debe tener exactamente 11 dígitos numéricos.
   */
  private String numRucEmisor;

  /**
   * Código del comprobante a enviar.
   * - 09: Guía de Remisión Electrónica (GRE) Remitente
   * - 31: Guía de Remisión Electrónica (GRE) Transportista
   */
  private String codCpe;

  /**
   * Número de serie del comprobante.
   * - Para GRE Remitente: T### (donde ### es un número de 3 dígitos)
   * - Para GRE Transportista: V### (donde ### es un número de 3 dígitos)
   */
  private String numSerie;

  /**
   * Número del comprobante.
   * Debe ser un número entre 1 y 8 dígitos numéricos.
   */
  private String numCpe;

  /**
   * Información del archivo a enviar.
   * Contiene el nombre del archivo, su contenido en Base64 y el hash SHA-256.
   */
  private Archivo archivo;

  /**
   * Información del archivo a enviar
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Archivo {

    /**
     * Nombre del archivo zip enviado.
     * Estructura: RRRRRRRRRRR-TT-SSSS-NNNNNNNN.zip
     * Donde:
     * - RRRRRRRRRR: Número de RUC, de 11 valores numéricos
     * - TT: Tipo de comprobante, solo se permite 09 y 31
     * - SSSS: Serie del comprobante, solo se permite T### y V###
     * - NNNNNNNN: Número de comprobante, se permite de 1 hasta 8 valores numéricos
     */
    private String nomArchivo;

    /**
     * Base 64 del archivo zip enviado
     */
    private String arcGreZip;

    /**
     * Hash del archivo zip enviado. Debe calcularse el hash del archivo zip
     * haciendo uso del algoritmo SHA-256
     */
    private String hashZip;
  }
}
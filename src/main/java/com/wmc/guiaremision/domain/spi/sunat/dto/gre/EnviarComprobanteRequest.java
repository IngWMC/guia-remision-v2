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
public class EnviarComprobanteRequest {

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

  /**
   * Información del archivo a enviar
   */
  private Archivo archivo;
}
package com.wmc.guiaremision.infrastructure.web.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de respuesta para la creación y actualización de parámetros de
 * configuración.
 * <p>
 * Contiene los datos del parámetro después de ser guardado en el sistema,
 * incluyendo el identificador generado.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
public class SaveParameterResponse {

  /**
   * Identificador único del parámetro generado.
   */
  private Integer codigoParametro;

  /**
   * RUC de la empresa asociada al parámetro.
   */
  private String rucEmpresa;

  /**
   * Nombre del archivo del logo.
   */
  private String nombreLogo;

  /**
   * Nombre del archivo del certificado digital.
   */
  private String nombreCertificado;

}

package com.wmc.guiaremision.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la creación y actualización de parámetros de configuración de una
 * empresa.
 * <p>
 * Contiene las rutas de almacenamiento de archivos y configuración del
 * certificado
 * digital necesario para la emisión de documentos electrónicos.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
public class SaveParameterRequest {

  /**
   * RUC (Registro Único de Contribuyente) de la empresa.
   * <p>
   * Debe ser un RUC válido de 11 dígitos que comience con:
   * <ul>
   * <li>10 - Persona natural con negocio</li>
   * <li>20 - Persona jurídica (empresa)</li>
   * </ul>
   * </p>
   * <p>
   * Ejemplo: {@code "20123456789"}
   * </p>
   */
  @NotBlank(message = "Debe ingresar el RUC de la empresa")
  @Pattern(regexp = "^(10|20)\\d{9}$", message = "El RUC debe tener 11 dígitos y comenzar con 10 o 20")
  private String rucEmpresa;

  /**
   * Nombre del archivo del logo de la empresa (incluye extensión).
   * <p>
   * Ejemplo: {@code "logo_empresa.png"}
   * </p>
   */
  @NotBlank(message = "Debe ingresar el nombre del archivo del logo")
  private String nombreLogo;

  /**
   * Nombre del archivo del certificado digital (.pfx o .p12).
   * <p>
   * Ejemplo: {@code "certificado_20123456789.pfx"}
   * </p>
   */
  @NotBlank(message = "Debe ingresar el nombre del archivo del certificado digital")
  private String nombreCertificado;

  /**
   * Contraseña del certificado digital para firma electrónica.
   * <p>
   * <strong>Información sensible:</strong> Este valor será cifrado antes de
   * almacenarse.
   * </p>
   */
  @NotBlank(message = "Debe ingresar la contraseña del certificado digital")
  private String contrasenaCertificado;

}

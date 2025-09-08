package com.wmc.guiaremision.infrastructure.ubl.common;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identificador de código de ubigeo para documentos UBL.
 * 
 * <p>
 * Representa el código de ubigeo que identifica de manera única
 * una ubicación geográfica en el Perú (departamento, provincia, distrito).
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class UbigeoId {
  /**
   * Código de ubigeo.
   * 
   * <p>
   * Código de 6 dígitos que identifica de manera única una ubicación
   * geográfica en el Perú según el estándar oficial de ubigeo.
   * </p>
   */
  @XmlValue
  private String value;

  /**
   * Nombre de la agencia del esquema.
   * 
   * <p>
   * Identifica la agencia responsable del esquema de codificación
   * utilizado para el código de ubigeo.
   * </p>
   */
  @XmlAttribute(name = "schemeAgencyName")
  private final String schemeAgencyName = UblAttributesConstant.schemeAgencyNameUbigeo;

  /**
   * Nombre del esquema de codificación.
   * 
   * <p>
   * Especifica el esquema utilizado para la codificación
   * del código de ubigeo según los estándares oficiales.
   * </p>
   */
  @XmlAttribute(name = "schemeName")
  private final String schemeName = UblAttributesConstant.schemeNameUbigeo;
}

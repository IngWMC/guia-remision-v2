package com.wmc.guiaremision.infrastructure.ubl.common;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

/**
 * Identificador de una entidad (Party).
 * 
 * <p>
 * Representa el número de documento de identidad de una entidad (RUC, DNI,
 * etc.)
 * junto con el esquema utilizado para su validación.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PartyIdentificationId {
  /**
   * Número de documento de identidad.
   */
  @XmlValue
  private String value;

  /**
   * URI del esquema de identificación.
   */
  @XmlAttribute(name = "schemeURI")
  private final String schemeURI = UblAttributesConstant.schemeUri;

  /**
   * Nombre de la agencia que mantiene el esquema.
   */
  @XmlAttribute(name = "schemeAgencyName")
  private final String schemeAgencyName = UblAttributesConstant.schemeAgencyName;

  /**
   * Nombre del esquema de identificación.
   */
  @XmlAttribute(name = "schemeName")
  private final String schemeName = UblAttributesConstant.schemeName;

  /**
   * Código del esquema de identificación.
   */
  @XmlAttribute(name = "schemeID")
  private String schemeID;
}

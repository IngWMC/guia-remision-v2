package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Código de motivo de traslado.
 * 
 * <p>
 * Especifica el motivo por el cual se realiza el traslado de mercancías
 * según los catálogos oficiales de SUNAT.
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
public class HandlingCode {

  /**
   * Código del motivo de traslado.
   */
  @XmlValue
  private String value;

  /**
   * URI de la lista de códigos de motivo de traslado.
   */
  @XmlAttribute(name = "listURI")
  private final String listURI = UblAttributesConstant.listURIHandlingCode;

  /**
   * Nombre de la lista de códigos.
   */
  @XmlAttribute(name = "listName")
  private final String listName = UblAttributesConstant.listNameHandlingCode;

  /**
   * Nombre de la agencia que mantiene la lista.
   */
  @XmlAttribute(name = "listAgencyName")
  private final String listAgencyName = UblAttributesConstant.listAgencyName;

}

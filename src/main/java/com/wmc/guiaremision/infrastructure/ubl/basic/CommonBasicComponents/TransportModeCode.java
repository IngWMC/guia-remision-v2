package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

/**
 * Código de modalidad de transporte.
 * 
 * <p>
 * Especifica el medio de transporte utilizado para el traslado de mercancías
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
public class TransportModeCode {

  /**
   * Código de la modalidad de transporte.
   */
  @XmlValue
  private String value;

  /**
   * URI de la lista de códigos de modalidad de transporte.
   */
  @XmlAttribute(name = "listID")
  private final String listURI = UblAttributesConstant.listURITransportModeCode;

  /**
   * Nombre de la lista de códigos.
   */
  @XmlAttribute(name = "listName")
  private final String listName = UblAttributesConstant.listNameTransportModeCode;

  /**
   * Nombre de la agencia que mantiene la lista.
   */
  @XmlAttribute(name = "listAgencyName")
  private final String listAgencyName = UblAttributesConstant.listAgencyName;
}

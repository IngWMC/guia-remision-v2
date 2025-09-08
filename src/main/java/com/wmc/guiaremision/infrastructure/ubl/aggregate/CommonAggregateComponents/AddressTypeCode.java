package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

/**
 * Código de tipo de dirección para documentos UBL.
 * 
 * <p>
 * Especifica el tipo de establecimiento o dirección según los catálogos
 * oficiales de SUNAT, permitiendo clasificar diferentes tipos de ubicaciones.
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
public class AddressTypeCode {

  /**
   * Código del tipo de establecimiento.
   * 
   * <p>
   * Código que identifica el tipo de establecimiento según los catálogos
   * oficiales de SUNAT.
   * </p>
   */
  @XmlValue
  private String value;

  /**
   * Identificador de la lista de códigos.
   */
  @XmlAttribute(name = "listID")
  private String listID;

  /**
   * Nombre de la agencia que mantiene la lista.
   */
  @XmlAttribute(name = "listAgencyName")
  private final String listAgencyName = UblAttributesConstant.listAgencyName;

  /**
   * Nombre de la lista de códigos.
   */
  @XmlAttribute(name = "listName")
  private final String listName = UblAttributesConstant.listNameAddressTypeCode;
}

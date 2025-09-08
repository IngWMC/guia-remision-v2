package com.wmc.guiaremision.infrastructure.ubl.common;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

/**
 * Representa una cantidad con su unidad de medida.
 * 
 * <p>
 * Utilizada para especificar cantidades de productos o servicios en los
 * documentos UBL,
 * incluyendo la unidad de medida según los catálogos oficiales de SUNAT.
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
public class Quantity {
  /**
   * Valor de la cantidad.
   * 
   * <p>
   * Número decimal positivo que representa la cantidad.
   * </p>
   */
  @XmlValue
  private String value;

  /**
   * Código de la unidad de medida.
   * 
   * <p>
   * Código de 3 caracteres según catálogo UN/ECE rec 20.
   * </p>
   */
  @XmlAttribute(name = "unitCode")
  private String unitCode;

  /**
   * Nombre de la agencia que mantiene la lista de códigos de unidad.
   */
  @XmlAttribute(name = "unitCodeListAgencyName")
  private final String unitCodeListAgencyName = UblAttributesConstant.unitCodeListAgencyName;

  /**
   * Identificador de la lista de códigos de unidad.
   */
  @XmlAttribute(name = "unitCodeListID")
  private final String unitCodeListID = UblAttributesConstant.unitCodeListID;
}

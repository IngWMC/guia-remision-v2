package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import java.math.BigDecimal;

/**
 * Medida de peso bruto para documentos UBL.
 * 
 * <p>
 * Representa el peso bruto total de las mercancías transportadas,
 * incluyendo el peso de los productos y sus embalajes.
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
public class GrossWeightMeasure {
  /**
   * Valor del peso bruto.
   * 
   * <p>
   * Número decimal positivo que representa el peso bruto total
   * de las mercancías en la unidad especificada.
   * </p>
   */
  @XmlValue
  private BigDecimal value;

  /**
   * Código de unidad de medida.
   * 
   * <p>
   * Código obligatorio que especifica la unidad de medida del peso.
   * Valores permitidos: "KGM" (kilogramos) o "TNE" (toneladas).
   * </p>
   */
  @XmlAttribute(name = "unitCode")
  private String unitCode;
}

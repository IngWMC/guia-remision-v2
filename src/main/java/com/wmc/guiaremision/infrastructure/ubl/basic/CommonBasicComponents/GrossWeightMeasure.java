package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class GrossWeightMeasure {
  /**
   * Peso bruto total (decimal positivo)
   */
  @NotNull(message = "Debe indicar el peso bruto total")
  @XmlValue
  private BigDecimal value;

  /**
   * Unidad de medida (Obligatorio, 'KGM' o 'TNE')
   * ERROR 2881/2523
   */
  @NotBlank(message = "Es obligatorio indicar la unidad de medida del Peso Total de la guía")
  @Pattern(regexp = "KGM|TNE", message = "La unidad de medida del 'Peso bruto total de los items seleccionados' debe ser kilogramos (KGM) o toneladas (TNE)")
  @XmlAttribute(name = "unitCode")
  private String unitCode;
}

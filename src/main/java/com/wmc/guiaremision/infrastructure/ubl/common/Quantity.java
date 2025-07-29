package com.wmc.guiaremision.infrastructure.ubl.common;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Quantity {
  /**
   * Cantidad entregada (decimal positivo)
   */
  @NotNull(message = "Debe indicar la cantidad entregada")
  @XmlValue
  private String value;

  /**
   * Unidad de medida (Obligatorio, Catálogo 03/65, 3 caracteres)
   * ERROR 2883/3446
   */
  @NotBlank(message = "Es obligatorio indicar la unidad de medida del ítem")
  @Size(max = 3, message = "Unidad de medida debe tener máximo 3 caracteres")
  @XmlAttribute(name = "unitCode", namespace = UblNamespacesConstant.CBC)
  private String unitCode;

  @XmlAttribute(name = "unitCodeListAgencyName", namespace = UblNamespacesConstant.CBC)
  private final String unitCodeListAgencyName = UblAttributesConstant.unitCodeListAgencyName;

  @XmlAttribute(name = "unitCodeListID", namespace = UblNamespacesConstant.CBC)
  private final String unitCodeListID = UblAttributesConstant.unitCodeListID;
}

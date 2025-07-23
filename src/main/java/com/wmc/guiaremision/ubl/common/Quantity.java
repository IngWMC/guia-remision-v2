package com.wmc.guiaremision.ubl.common;

import com.wmc.guiaremision.application.common.constant.UblAttributesConstant;
import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Quantity {
  /**
   * Cantidad entregada (decimal positivo)
   */
  @NotNull(message = "Debe indicar la cantidad entregada")
  @XmlValue
  private BigDecimal value;

  /**
   * Unidad de medida (Obligatorio, Catálogo 03/65, 3 caracteres)
   * ERROR 2883/3446
   */
  @NotBlank(message = "Es obligatorio indicar la unidad de medida del ítem")
  @Size(max = 3, message = "Unidad de medida debe tener máximo 3 caracteres")
  @XmlAttribute(name = "unitCode", namespace = UblNamespacesConstant.CBC)
  private String unitCode;

  @XmlAttribute(name = "unitCodeListAgencyName", namespace = UblNamespacesConstant.CBC)
  private String unitCodeListAgencyName = UblAttributesConstant.unitCodeListAgencyName;

  @XmlAttribute(name = "unitCodeListID", namespace = UblNamespacesConstant.CBC)
  private String unitCodeListID = UblAttributesConstant.unitCodeListID;

  private Quantity(BigDecimal value, String unitCode) {
    this.value = value;
    this.unitCode = unitCode;
  }
}

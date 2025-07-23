package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.ubl.common.CDataAdapter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchLineItem {
  /**
   * Descripción detallada del bien (Obligatorio, 3-500 caracteres)
   * ERROR 2781/4084
   */
  @NotBlank(message = "El XML no contiene el atributo o no existe informacion de descripcion del items")
  @Size(min = 3, max = 500, message = "Descripción del Ítem - El dato ingresado no cumple con el formato establecido.")
  @XmlElement(name = "Description", namespace = UblNamespacesConstant.CBC)
  @XmlJavaTypeAdapter(CDataAdapter.class)
  private String description;

  /**
   * Código del bien (opcional, hasta 30 caracteres)
   * OBSERV 4085
   */
  @Size(max = 30, message = "Código del Ítem - El dato ingresado no cumple con el formato establecido.")
  @XmlElement(name = "SellersItemIdentification", namespace = UblNamespacesConstant.CAC)
  private SellersItemIdentification sellersItemIdentification;
}

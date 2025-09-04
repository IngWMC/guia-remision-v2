package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
  private SellersItemIdentification sellersItemIdentification = new SellersItemIdentification();
}

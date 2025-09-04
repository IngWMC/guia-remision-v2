package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import lombok.Data;

import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersItemIdentification {
  /**
   * Código del bien (opcional, hasta 30 caracteres)
   */
  @Size(max = 30, message = "Código del Ítem - El dato ingresado no cumple con el formato establecido.")
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  @XmlJavaTypeAdapter(CDataAdapter.class)
  private String id;
}

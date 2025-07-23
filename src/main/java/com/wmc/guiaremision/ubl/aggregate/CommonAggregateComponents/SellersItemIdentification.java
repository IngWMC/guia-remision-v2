package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.ubl.common.CDataAdapter;
import lombok.Data;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

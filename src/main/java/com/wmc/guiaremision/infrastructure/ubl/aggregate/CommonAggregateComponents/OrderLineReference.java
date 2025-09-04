package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderLineReference {

  @Pattern(regexp = "\\d{1,4}", message = "El Numero de orden referencial del item no cumple con el formato establecido")
  @XmlElement(name = "LineID", namespace = UblNamespacesConstant.CBC)
  private String lineID;

}

package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderLineReference {

  @Pattern(regexp = "\\d{1,4}", message = "El Numero de orden referencial del item no cumple con el formato establecido")
  @XmlElement(name = "LineID", namespace = UblNamespacesConstant.CBC)
  private String lineID;

}

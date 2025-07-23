package com.wmc.guiaremision.ubl.basic.CommonBasicComponents;

import com.wmc.guiaremision.application.common.constant.UblAttributesConstant;
import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportModeCode {

  @NotBlank(message = "No existe información de modalidad de transporte.")
  @Size(min = 2, max = 2, message = "El valor ingresado como modalidad de traslado no es valido")
  @XmlAttribute(name = "value", namespace = UblNamespacesConstant.CBC)
  private String value;

  @XmlAttribute(name = "listID", namespace = UblNamespacesConstant.CBC)
  private String listURI = UblAttributesConstant.listURITransportModeCode;

  @XmlAttribute(name = "listName", namespace = UblNamespacesConstant.CBC)
  private String listName = UblAttributesConstant.listNameTransportModeCode;

  @XmlAttribute(name = "listAgencyName", namespace = UblNamespacesConstant.CBC)
  private String listAgencyName = UblAttributesConstant.listAgencyName;
}

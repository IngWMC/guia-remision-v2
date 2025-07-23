package com.wmc.guiaremision.ubl.basic.CommonBasicComponents;

import com.wmc.guiaremision.application.common.constant.UblAttributesConstant;
import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class HandlingCode {

  @NotBlank(message = "El código de motivo de traslado es obligatorio")
  @Size(min = 2, max = 2, message = "El valor ingresado como motivo de traslado no es valido")
  @XmlValue
  private String value;

  @XmlAttribute(name = "listURI", namespace = UblNamespacesConstant.CBC)
  private String listURI = UblAttributesConstant.listURIHandlingCode;

  @XmlAttribute(name = "listName", namespace = UblNamespacesConstant.CBC)
  private String listName = UblAttributesConstant.listNameHandlingCode;

  @XmlAttribute(name = "listAgencyName", namespace = UblNamespacesConstant.CBC)
  private String listAgencyName = UblAttributesConstant.listAgencyName;

}

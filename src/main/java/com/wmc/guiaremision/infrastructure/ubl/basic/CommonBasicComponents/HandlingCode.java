package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class HandlingCode {

  @NotBlank(message = "El código de motivo de traslado es obligatorio")
  @Size(min = 2, max = 2, message = "El valor ingresado como motivo de traslado no es valido")
  @XmlValue
  private String value;

  @XmlAttribute(name = "listURI")
  private final String listURI = UblAttributesConstant.listURIHandlingCode;

  @XmlAttribute(name = "listName")
  private final String listName = UblAttributesConstant.listNameHandlingCode;

  @XmlAttribute(name = "listAgencyName")
  private final String listAgencyName = UblAttributesConstant.listAgencyName;

}

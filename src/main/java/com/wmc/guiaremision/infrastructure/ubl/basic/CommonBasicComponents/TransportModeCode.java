package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportModeCode {

  @NotBlank(message = "No existe información de modalidad de transporte.")
  @Size(min = 2, max = 2, message = "El valor ingresado como modalidad de traslado no es valido")
  @XmlValue
  private String value;

  @XmlAttribute(name = "listID")
  private final String listURI = UblAttributesConstant.listURITransportModeCode;

  @XmlAttribute(name = "listName")
  private final String listName = UblAttributesConstant.listNameTransportModeCode;

  @XmlAttribute(name = "listAgencyName")
  private final String listAgencyName = UblAttributesConstant.listAgencyName;
}

package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

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

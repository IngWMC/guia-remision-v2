package com.wmc.guiaremision.infrastructure.ubl.common;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class UbigeoId {
  /**
   * Código de ubigeo (obligatorio, 6 dígitos).
   */
  @NotBlank(message = "El código de ubigeo es obligatorio")
  @Pattern(regexp = "\\d{6}", message = "El código de ubigeo debe tener 6 dígitos")
  @XmlValue
  private String value;

  @XmlAttribute(name = "schemeAgencyName")
  private final String schemeAgencyName = UblAttributesConstant.schemeAgencyNameUbigeo;

  @XmlAttribute(name = "schemeName")
  private final String schemeName = UblAttributesConstant.schemeNameUbigeo;
}

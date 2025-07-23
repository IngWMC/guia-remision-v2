package com.wmc.guiaremision.ubl.common;

import com.wmc.guiaremision.application.common.constant.UblAttributesConstant;
import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.*;

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
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  @XmlValue
  private String id;

  @XmlAttribute(name = "schemeAgencyName")
  private String schemeAgencyName = UblAttributesConstant.schemeAgencyNameUbigeo;

  @XmlAttribute(name = "schemeName")
  private String schemeName = UblAttributesConstant.schemeNameUbigeo;
}

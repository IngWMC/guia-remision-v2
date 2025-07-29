package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressTypeCode {

  /**
   * Código de tipo de establecimiento (opcional, máximo 4 caracteres).
   */
  @Size(max = 4, message = "El código de tipo de establecimiento no debe superar los 4 caracteres")
  @XmlElement(name = "AddressTypeCode", namespace = UblNamespacesConstant.CBC)
  private String value;

  @XmlAttribute(name = "listID", namespace = UblNamespacesConstant.CBC)
  private String listID;

  @XmlAttribute(name = "listAgencyName", namespace = UblNamespacesConstant.CBC)
  private final String listAgencyName = UblAttributesConstant.listAgencyName;

  @XmlAttribute(name = "listName", namespace = UblNamespacesConstant.CBC)
  private final String listName = UblAttributesConstant.listNameAddressTypeCode;
}

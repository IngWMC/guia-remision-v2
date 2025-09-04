package com.wmc.guiaremision.infrastructure.ubl.common;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PartyIdentificationId {
  @XmlValue
  private String value;

  @XmlAttribute(name = "schemeURI")
  private final String schemeURI = UblAttributesConstant.schemeUri;

  @XmlAttribute(name = "schemeAgencyName")
  private final String schemeAgencyName = UblAttributesConstant.schemeAgencyName;

  @XmlAttribute(name = "schemeName")
  private final String schemeName = UblAttributesConstant.schemeName;

  @XmlAttribute(name = "schemeID")
  private String schemeID;
}

package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatoryParty", propOrder = { "partyIdentification", "partyName" })
public class SignatoryParty {
  @XmlElement(name = "PartyIdentification", namespace = UblNamespacesConstant.CAC)
  private PartyIdentification partyIdentification = new PartyIdentification();

  @XmlElement(name = "PartyName", namespace = UblNamespacesConstant.CAC)
  private PartyName partyName = new PartyName();
}
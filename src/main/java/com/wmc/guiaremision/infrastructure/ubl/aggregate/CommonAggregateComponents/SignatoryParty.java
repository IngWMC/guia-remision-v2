package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatoryParty", propOrder = { "partyIdentification", "partyName" })
public class SignatoryParty {
  @XmlElement(name = "PartyIdentification", namespace = UblNamespacesConstant.CAC)
  private PartyIdentification partyIdentification;

  @XmlElement(name = "PartyName", namespace = UblNamespacesConstant.CAC)
  private PartyName partyName;
}
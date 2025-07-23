package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;

@Data
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
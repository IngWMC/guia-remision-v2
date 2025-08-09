package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Signature", propOrder = { "id", "note", "signatoryParty", "digitalSignatureAttachment" })
public class Signature {
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  private String id = "signatureINGWMC";

  @XmlElement(name = "Note", namespace = UblNamespacesConstant.CBC)
  private String note = "INGWMC";

  @XmlElement(name = "SignatoryParty", namespace = UblNamespacesConstant.CAC)
  private SignatoryParty signatoryParty = new SignatoryParty();

  @XmlElement(name = "DigitalSignatureAttachment", namespace = UblNamespacesConstant.CAC)
  private DigitalSignatureAttachment digitalSignatureAttachment = new DigitalSignatureAttachment();
}
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
@XmlType(name = "DigitalSignatureAttachment", propOrder = { "externalReference" })
public class DigitalSignatureAttachment {
  @XmlElement(name = "ExternalReference", namespace = UblNamespacesConstant.CAC)
  private ExternalReference externalReference = new ExternalReference();
}
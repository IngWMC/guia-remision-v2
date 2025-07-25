package com.wmc.guiaremision.infrastructure.ubl.extension.CommonExtensionComponents;

import com.wmc.guiaremision.infrastructure.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.Signature;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtensionContent", propOrder = { "signature" })
public class ExtensionContent {
  @XmlElement(name = "Signature", namespace = UblNamespacesConstant.DS)
  private Signature signature;
}
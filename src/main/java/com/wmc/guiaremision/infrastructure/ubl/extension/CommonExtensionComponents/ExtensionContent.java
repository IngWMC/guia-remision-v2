package com.wmc.guiaremision.infrastructure.ubl.extension.CommonExtensionComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
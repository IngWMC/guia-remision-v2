package com.wmc.guiaremision.infrastructure.ubl.extension.CommonExtensionComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UBLExtensions", propOrder = { "ublExtension" })
public class UblExtensions {
  @XmlElement(name = "UBLExtension", namespace = UblNamespacesConstant.EXT)
  private UblExtension ublExtension = new UblExtension();
}
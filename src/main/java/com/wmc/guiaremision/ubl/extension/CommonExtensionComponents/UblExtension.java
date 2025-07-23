package com.wmc.guiaremision.ubl.extension.CommonExtensionComponents;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UBLExtension", propOrder = { "extensionContent" })
public class UblExtension {
  @XmlElement(name = "ExtensionContent", namespace = UblNamespacesConstant.EXT)
  private ExtensionContent extensionContent;
}
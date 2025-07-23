package com.wmc.guiaremision.ubl.extension.CommonExtensionComponents;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UBLExtensions", propOrder = { "ublExtension" })
public class UblExtensions {
  @XmlElement(name = "UBLExtension", namespace = UblNamespacesConstant.EXT)
  private List<UblExtension> ublExtension = new ArrayList<>();
}
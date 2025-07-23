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
@XmlType(name = "ExternalReference", propOrder = { "uri" })
public class ExternalReference {
  @XmlElement(name = "URI", namespace = UblNamespacesConstant.CBC)
  private String uri = "#signatureWMC";
}
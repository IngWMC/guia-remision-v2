package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyName", propOrder = { "name" })
public class PartyName {
  @XmlElement(name = "Name", namespace = UblNamespacesConstant.CBC)
  @XmlJavaTypeAdapter(CDataAdapter.class)
  private String name;
}
package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Datos del destinatario
 * Incluye identificación y razón social del transportista, con validaciones SUNAT
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryCustomerParty {

  @XmlElement(name = "Party", namespace = UblNamespacesConstant.CAC)
  private Party party = new Party();

}

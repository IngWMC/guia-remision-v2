package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.common.constant.UblNamespacesConstant;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Datos del destinatario
 * Incluye identificación y razón social del transportista, con validaciones SUNAT
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryCustomerParty {

  @XmlElement(name = "Party", namespace = UblNamespacesConstant.CAC)
  private Party party;

}

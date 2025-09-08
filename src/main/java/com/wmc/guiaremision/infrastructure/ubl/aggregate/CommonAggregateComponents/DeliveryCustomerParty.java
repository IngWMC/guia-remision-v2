package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Entidad destinataria para documentos UBL.
 * 
 * <p>
 * Representa la entidad que recibirá las mercancías en el destino,
 * incluyendo su identificación legal y datos de contacto.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryCustomerParty {

  /**
   * Información completa de la entidad destinataria.
   * 
   * <p>
   * Contiene todos los datos de la entidad que recibirá las mercancías,
   * incluyendo identificación, razón social y datos legales.
   * </p>
   */
  @XmlElement(name = "Party", namespace = UblNamespacesConstant.CAC)
  private Party party = new Party();

}

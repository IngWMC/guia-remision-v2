package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Información de entrega para documentos UBL.
 * 
 * <p>
 * Representa los detalles de la entrega de mercancías, incluyendo la dirección
 * de destino y la información de partida asociada al proceso de entrega.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Delivery {
  /**
   * Dirección de llegada.
   * 
   * <p>
   * Dirección física donde se realizará la entrega de las mercancías,
   * incluyendo información de ubicación y coordenadas.
   * </p>
   */
  @XmlElement(name = "DeliveryAddress", namespace = UblNamespacesConstant.CAC)
  private DeliveryAddress deliveryAddress = new DeliveryAddress();

  /**
   * Información de despacho asociada a la entrega.
   * 
   * <p>
   * Detalles sobre el despacho que se relaciona con esta entrega,
   * incluyendo fechas y condiciones específicas.
   * </p>
   */
  @XmlElement(name = "Despatch", namespace = UblNamespacesConstant.CAC)
  private Despatch despatch = new Despatch();
}
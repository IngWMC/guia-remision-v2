package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Subcomponente Delivery según UBL/SUNAT.
 * Representa la información de entrega (llegada) de la guía de remisión.
 * Incluye dirección de llegada, despatch (partida), y validaciones de
 * obligatoriedad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Delivery {
  /**
   * Dirección de llegada (obligatoria).
   */
  @NotNull(message = "La dirección de llegada es obligatoria")
  @Valid
  @XmlElement(name = "DeliveryAddress", namespace = UblNamespacesConstant.CAC)
  private DeliveryAddress deliveryAddress = new DeliveryAddress();

  /**
   * Información de partida (despatch) asociada a la entrega (opcional).
   */
  @Valid
  @XmlElement(name = "Despatch", namespace = UblNamespacesConstant.CAC)
  private Despatch despatch = new Despatch();
}
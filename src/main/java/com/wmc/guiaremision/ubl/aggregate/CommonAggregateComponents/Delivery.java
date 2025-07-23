package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

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
  private DeliveryAddress deliveryAddress;

  /**
   * Información de partida (despatch) asociada a la entrega (opcional).
   */
  @Valid
  @XmlElement(name = "Despatch", namespace = UblNamespacesConstant.CAC)
  private Despatch despatch;
}
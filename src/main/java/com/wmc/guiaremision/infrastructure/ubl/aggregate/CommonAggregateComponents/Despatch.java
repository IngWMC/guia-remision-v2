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
 * Subcomponente Despatch según UBL/SUNAT.
 * Representa la información de partida asociada a la entrega de la guía de
 * remisión.
 * Incluye dirección de partida y validaciones de obligatoriedad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Despatch {
  /**
   * Dirección de partida (obligatoria).
   */
  @NotNull(message = "La dirección de partida es obligatoria")
  @Valid
  @XmlElement(name = "DespatchAddress", namespace = UblNamespacesConstant.CAC)
  private DespatchAddress despatchAddress = new DespatchAddress();
}
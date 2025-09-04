package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Representa la línea de dirección (AddressLine) para UBL/SUNAT.
 * Usado en direcciones de partida y llegada de la guía de remisión.
 * Obligatorio, máximo 500 caracteres.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressLine {
  /**
   * Dirección completa (obligatoria, máximo 500 caracteres).
   */
  @NotBlank(message = "La dirección es obligatoria")
  @Size(max = 500, message = "La dirección no debe superar los 500 caracteres")
  @XmlElement(name = "Line", namespace = UblNamespacesConstant.CBC)
  @XmlJavaTypeAdapter(CDataAdapter.class)
  private String line;
}
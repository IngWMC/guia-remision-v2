package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.UbigeoId;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * Dirección de partida (DespatchAddress) según UBL/SUNAT.
 * Incluye código de ubigeo, dirección, tipo de establecimiento y coordenadas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchAddress {
  /**
   * Código de ubigeo (obligatorio, 6 dígitos).
   */
  @NotBlank(message = "El código de ubigeo es obligatorio")
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  private UbigeoId id = new UbigeoId();

  /**
   * Código de tipo de establecimiento (opcional, máximo 4 caracteres).
   */
  @XmlElement(name = "AddressTypeCode", namespace = UblNamespacesConstant.CBC)
  private AddressTypeCode addressTypeCode = new AddressTypeCode();

  /**
   * Dirección completa (obligatoria, máximo 500 caracteres).
   */
  @XmlElement(name = "AddressLine", namespace = UblNamespacesConstant.CAC)
  private AddressLine addressLine = new AddressLine();

  /**
   * Coordenadas de ubicación (opcional).
   */
  @XmlElement(name = "LocationCoordinate", namespace = UblNamespacesConstant.CAC)
  private LocationCoordinate locationCoordinate;
}
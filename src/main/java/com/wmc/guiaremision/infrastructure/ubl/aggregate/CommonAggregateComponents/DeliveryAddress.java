package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.UbigeoId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.*;

/**
 * Dirección de llegada (DeliveryAddress) según UBL/SUNAT.
 * Incluye código de ubigeo, dirección, tipo de establecimiento y coordenadas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryAddress {
  /**
   * Código de ubigeo (obligatorio, 6 dígitos).
   */
  @NotBlank(message = "El código de ubigeo es obligatorio")
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  private UbigeoId id;

  /**
   * Código de tipo de establecimiento (opcional, máximo 4 caracteres).
   */
  @XmlElement(name = "AddressTypeCode", namespace = UblNamespacesConstant.CBC)
  private AddressTypeCode addressTypeCode;

  /**
   * Dirección completa (obligatoria, máximo 500 caracteres).
   */
  @XmlElement(name = "AddressLine", namespace = UblNamespacesConstant.CAC)
  private AddressLine addressLine;

  /**
   * Coordenadas de ubicación (opcional).
   */
  @XmlElement(name = "LocationCoordinate", namespace = UblNamespacesConstant.CAC)
  private LocationCoordinate locationCoordinate;
}
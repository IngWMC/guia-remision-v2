package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.UbigeoId;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dirección de entrega para documentos UBL.
 * 
 * <p>
 * Representa la dirección donde se realizará la entrega de las mercancías,
 * incluyendo información de ubicación, tipo de establecimiento y coordenadas
 * geográficas.
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
public class DeliveryAddress {
  /**
   * Código de ubigeo de la dirección de entrega.
   * 
   * <p>
   * Código que identifica la ubicación geográfica según el sistema
   * de codificación de ubigeos de Perú.
   * </p>
   */
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  private UbigeoId id = new UbigeoId();

  /**
   * Código del tipo de establecimiento de entrega.
   * 
   * <p>
   * Clasifica el tipo de establecimiento donde se realizará
   * la entrega de las mercancías.
   * </p>
   */
  @XmlElement(name = "AddressTypeCode", namespace = UblNamespacesConstant.CBC)
  private AddressTypeCode addressTypeCode;

  /**
   * Línea de dirección completa de entrega.
   * 
   * <p>
   * Dirección física detallada donde se realizará la entrega,
   * incluyendo calle, número, distrito, etc.
   * </p>
   */
  @XmlElement(name = "AddressLine", namespace = UblNamespacesConstant.CAC)
  private AddressLine addressLine = new AddressLine();

  /**
   * Coordenadas geográficas de la ubicación de entrega.
   * 
   * <p>
   * Coordenadas GPS o geográficas que permiten ubicar con precisión
   * el punto de entrega de las mercancías.
   * </p>
   */
  @XmlElement(name = "LocationCoordinate", namespace = UblNamespacesConstant.CAC)
  private LocationCoordinate locationCoordinate = new LocationCoordinate();
}
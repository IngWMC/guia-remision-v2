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
 * Dirección de partida para documentos UBL.
 * 
 * <p>
 * Representa la dirección desde donde se inicia el traslado de mercancías,
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
public class DespatchAddress {
  /**
   * Código de ubigeo de la dirección de partida.
   * 
   * <p>
   * Código que identifica la ubicación geográfica según el sistema
   * de codificación de ubigeos de Perú.
   * </p>
   */
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  private UbigeoId id = new UbigeoId();

  /**
   * Código del tipo de establecimiento.
   * 
   * <p>
   * Clasifica el tipo de establecimiento desde donde se realiza
   * la partida de las mercancías.
   * </p>
   */
  @XmlElement(name = "AddressTypeCode", namespace = UblNamespacesConstant.CBC)
  private AddressTypeCode addressTypeCode = new AddressTypeCode();

  /**
   * Línea de dirección completa.
   * 
   * <p>
   * Dirección física detallada desde donde se inicia el traslado,
   * incluyendo calle, número, distrito, etc.
   * </p>
   */
  @XmlElement(name = "AddressLine", namespace = UblNamespacesConstant.CAC)
  private AddressLine addressLine = new AddressLine();

  /**
   * Coordenadas geográficas de la ubicación.
   * 
   * <p>
   * Coordenadas GPS o geográficas que permiten ubicar con precisión
   * el punto de partida del traslado.
   * </p>
   */
  @XmlElement(name = "LocationCoordinate", namespace = UblNamespacesConstant.CAC)
  private LocationCoordinate locationCoordinate;
}
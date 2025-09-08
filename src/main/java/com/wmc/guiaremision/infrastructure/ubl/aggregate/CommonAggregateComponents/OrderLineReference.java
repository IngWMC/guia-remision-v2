package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * Referencia a una línea de pedido.
 * 
 * <p>
 * Establece una relación entre una línea de despacho y una línea de pedido
 * correspondiente, permitiendo el seguimiento y trazabilidad.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderLineReference {

  /**
   * Identificador de la línea de pedido.
   */
  @XmlElement(name = "LineID", namespace = UblNamespacesConstant.CBC)
  private String lineID;

}

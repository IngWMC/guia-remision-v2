package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Detalle del ítem en línea de despacho.
 * 
 * <p>
 * Contiene la información específica de cada producto o servicio incluido
 * en una línea de despacho, incluyendo descripción e identificadores.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchLineItem {
  /**
   * Descripción detallada del producto o servicio.
   * 
   * <p>
   * Descripción completa del bien que se está despachando, con soporte
   * para caracteres especiales mediante CDATA.
   * </p>
   */
  @XmlElement(name = "Description", namespace = UblNamespacesConstant.CBC)
  @XmlJavaTypeAdapter(CDataAdapter.class)
  private String description;

  /**
   * Identificación del ítem por parte del vendedor.
   * 
   * <p>
   * Código o identificador que el vendedor asigna al producto o servicio
   * para su identificación interna.
   * </p>
   */
  @XmlElement(name = "SellersItemIdentification", namespace = UblNamespacesConstant.CAC)
  private SellersItemIdentification sellersItemIdentification = new SellersItemIdentification();
}

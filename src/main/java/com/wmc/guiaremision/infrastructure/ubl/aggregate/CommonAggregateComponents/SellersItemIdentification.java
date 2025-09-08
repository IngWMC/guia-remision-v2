package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Identificación del ítem por parte del vendedor para documentos UBL.
 * 
 * <p>
 * Representa el código o identificador que el vendedor asigna internamente
 * a un producto o servicio para su identificación y seguimiento en sus
 * sistemas de gestión.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersItemIdentification {
  /**
   * Identificador del ítem asignado por el vendedor.
   * 
   * <p>
   * Código único que el vendedor utiliza para identificar el producto
   * o servicio en sus sistemas internos, con soporte para caracteres
   * especiales mediante CDATA.
   * </p>
   */
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  @XmlJavaTypeAdapter(CDataAdapter.class)
  private String id;
}

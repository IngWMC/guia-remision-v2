package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Línea de dirección para documentos UBL.
 * 
 * <p>
 * Representa una línea específica de dirección utilizada en direcciones de
 * partida y llegada de la guía de remisión, con soporte para caracteres
 * especiales mediante CDATA.
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
public class AddressLine {
  /**
   * Texto de la línea de dirección.
   * 
   * <p>
   * Contiene la información específica de la dirección, con máximo
   * 500 caracteres y soporte para caracteres especiales.
   * </p>
   */
  @XmlElement(name = "Line", namespace = UblNamespacesConstant.CBC)
  @XmlJavaTypeAdapter(CDataAdapter.class)
  private String line;
}
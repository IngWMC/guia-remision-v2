package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Información de despacho para documentos UBL.
 * 
 * <p>
 * Representa los detalles del despacho o partida de mercancías, incluyendo
 * la dirección desde donde se inicia el traslado y las condiciones
 * específicas del despacho.
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
public class Despatch {
  /**
   * Dirección de despacho o partida.
   * 
   * <p>
   * Dirección física desde donde se realiza el despacho de las mercancías,
   * incluyendo información de ubicación y coordenadas geográficas.
   * </p>
   */
  @XmlElement(name = "DespatchAddress", namespace = UblNamespacesConstant.CAC)
  private DespatchAddress despatchAddress = new DespatchAddress();
}
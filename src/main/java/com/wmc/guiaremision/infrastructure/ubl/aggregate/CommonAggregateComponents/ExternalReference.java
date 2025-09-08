package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Referencia externa a un recurso.
 * 
 * <p>
 * Representa una referencia a un recurso externo, como un archivo de firma
 * digital, mediante una URI.
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
@XmlType(name = "ExternalReference", propOrder = { "uri" })
public class ExternalReference {
  /**
   * URI que apunta al recurso externo.
   */
  @XmlElement(name = "URI", namespace = UblNamespacesConstant.CBC)
  private String uri = "#signatureWMC";
}
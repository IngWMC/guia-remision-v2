package com.wmc.guiaremision.infrastructure.ubl.extension.CommonExtensionComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.Signature;

/**
 * Contenido de una extensión UBL.
 * 
 * <p>
 * Contiene la información específica de la extensión, incluyendo
 * elementos como firmas digitales u otros datos adicionales.
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
@XmlType(name = "ExtensionContent", propOrder = { "signature" })
public class ExtensionContent {
  /**
   * Firma digital asociada a la extensión.
   */
  @XmlElement(name = "Signature", namespace = UblNamespacesConstant.DS)
  private Signature signature;
}
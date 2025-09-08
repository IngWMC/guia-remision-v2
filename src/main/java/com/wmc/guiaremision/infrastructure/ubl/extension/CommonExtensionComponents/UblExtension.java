package com.wmc.guiaremision.infrastructure.ubl.extension.CommonExtensionComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Extensión UBL individual.
 * 
 * <p>
 * Representa una extensión específica que puede ser agregada al documento UBL
 * para incluir información adicional no cubierta por el estándar.
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
@XmlType(name = "UBLExtension", propOrder = { "extensionContent" })
public class UblExtension {
  /**
   * Contenido de la extensión.
   */
  @XmlElement(name = "ExtensionContent", namespace = UblNamespacesConstant.EXT)
  private ExtensionContent extensionContent = new ExtensionContent();
}
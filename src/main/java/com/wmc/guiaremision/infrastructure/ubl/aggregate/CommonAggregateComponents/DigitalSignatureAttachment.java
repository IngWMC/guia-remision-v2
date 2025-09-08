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
 * Adjunto de firma digital.
 * 
 * <p>
 * Contiene la referencia externa a la firma digital del documento,
 * incluyendo la URI que apunta al archivo de firma.
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
@XmlType(name = "DigitalSignatureAttachment", propOrder = { "externalReference" })
public class DigitalSignatureAttachment {
  /**
   * Referencia externa a la firma digital.
   */
  @XmlElement(name = "ExternalReference", namespace = UblNamespacesConstant.CAC)
  private ExternalReference externalReference = new ExternalReference();
}
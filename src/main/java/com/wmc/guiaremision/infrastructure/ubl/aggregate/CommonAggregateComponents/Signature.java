package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Firma digital del documento UBL.
 * 
 * <p>
 * Contiene toda la información necesaria para la firma digital del documento,
 * incluyendo el identificador, nota, parte firmante y adjunto de firma digital.
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
@XmlType(name = "Signature", propOrder = { "id", "note", "signatoryParty", "digitalSignatureAttachment" })
public class Signature {
  /**
   * Identificador único de la firma.
   */
  @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
  private String id = "signatureINGWMC";

  /**
   * Nota o descripción de la firma.
   */
  @XmlElement(name = "Note", namespace = UblNamespacesConstant.CBC)
  private String note = "INGWMC";

  /**
   * Información de la parte que firma el documento.
   */
  @XmlElement(name = "SignatoryParty", namespace = UblNamespacesConstant.CAC)
  private SignatoryParty signatoryParty = new SignatoryParty();

  /**
   * Adjunto de la firma digital.
   */
  @XmlElement(name = "DigitalSignatureAttachment", namespace = UblNamespacesConstant.CAC)
  private DigitalSignatureAttachment digitalSignatureAttachment = new DigitalSignatureAttachment();
}
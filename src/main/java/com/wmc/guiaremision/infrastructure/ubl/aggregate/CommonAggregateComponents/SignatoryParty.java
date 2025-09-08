package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Parte firmante del documento.
 * 
 * <p>
 * Representa la entidad que firma digitalmente el documento UBL,
 * incluyendo su identificación y nombre.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatoryParty", propOrder = { "partyIdentification", "partyName" })
public class SignatoryParty {
  /**
   * Identificación de la parte firmante.
   */
  @XmlElement(name = "PartyIdentification", namespace = UblNamespacesConstant.CAC)
  private PartyIdentification partyIdentification = new PartyIdentification();

  /**
   * Nombre de la parte firmante.
   */
  @XmlElement(name = "PartyName", namespace = UblNamespacesConstant.CAC)
  private PartyName partyName = new PartyName();
}
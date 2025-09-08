package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Entidad Party para documentos UBL/SUNAT.
 * 
 * <p>
 * Representa una entidad (persona natural o jurídica) que participa en la
 * transacción.
 * Puede ser remitente, destinatario, proveedor, comprador, transportista, etc.
 * </p>
 * 
 * <p>
 * Incluye información de identificación y datos legales de la entidad.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Party {
    /**
     * Identificación de la entidad.
     * 
     * <p>
     * Contiene el número de documento de identidad (RUC, DNI, etc.) y el esquema
     * utilizado.
     * </p>
     */
    @XmlElement(name = "PartyIdentification", namespace = UblNamespacesConstant.CAC)
    private PartyIdentification partyIdentification = new PartyIdentification();

    /**
     * Información legal de la entidad.
     * 
     * <p>
     * Incluye el nombre legal o razón social de la entidad.
     * </p>
     */
    @XmlElement(name = "PartyLegalEntity", namespace = UblNamespacesConstant.CAC)
    private PartyLegalEntity partyLegalEntity = new PartyLegalEntity();

}
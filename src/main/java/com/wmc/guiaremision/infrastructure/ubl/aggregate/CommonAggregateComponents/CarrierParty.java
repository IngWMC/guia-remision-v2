package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Entidad transportista para documentos UBL.
 * 
 * <p>
 * Representa la empresa o entidad responsable del transporte de las mercancías,
 * incluyendo su identificación legal y registro MTC (Ministerio de Transportes
 * y Comunicaciones).
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CarrierParty {
    /**
     * Identificación del transportista.
     * 
     * <p>
     * Contiene el número de documento de identidad del transportista
     * (RUC, DNI, etc.) y el esquema utilizado.
     * </p>
     */
    @XmlElement(name = "PartyIdentification", namespace = UblNamespacesConstant.CAC)
    private PartyIdentification partyIdentification = new PartyIdentification();

    /**
     * Información legal del transportista.
     * 
     * <p>
     * Incluye la razón social y el número de registro MTC de la empresa
     * transportista.
     * </p>
     */
    @XmlElement(name = "PartyLegalEntity", namespace = UblNamespacesConstant.CAC)
    private PartyLegalEntity partyLegalEntity = new PartyLegalEntity();
}
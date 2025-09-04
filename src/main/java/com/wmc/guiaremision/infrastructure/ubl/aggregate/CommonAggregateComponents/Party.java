package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Entidad Party para UBL/SUNAT (remitente, destinatario, proveedor, comprador)
 * Incluye validaciones de identificación, nombre legal, atributos scheme, etc.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Party {
    /**
     * Identificación de la entidad (RUC, DNI, etc.)
     * ERROR 2678/2511/2759/2760/3417/2757/2555/2554/2758/3443/3444/2758
     */
    @NotNull(message = "Debe especificar la identificación del transportista (PartyIdentification)")
    @XmlElement(name = "PartyIdentification", namespace = UblNamespacesConstant.CAC)
    private PartyIdentification partyIdentification = new PartyIdentification();

    /**
     * Nombre legal de la entidad (Obligatorio, hasta 250 caracteres)
     * ERROR 1037/4338/2761/4152/3449/4106/3339/4381
     */
    @NotNull(message = "El XML no contiene el tag o no existe informacion de RegistrationName del emisor/destinatario/proveedor/comprador")
    @XmlElement(name = "PartyLegalEntity", namespace = UblNamespacesConstant.CAC)
    private PartyLegalEntity partyLegalEntity = new PartyLegalEntity();

}
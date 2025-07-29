package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

/**
 * Conductor (DriverPerson) para UBL/SUNAT
 * Incluye tipo y número de documento, nombres, apellidos, licencia y tipo de conductor
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DriverPerson {
    /**
     * Número de documento de identidad del conductor
     */
    @Size(max = 15, message = "El número de documento no debe superar los 15 caracteres")
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private PartyIdentification id;

    /**
     * Nombres del conductor
     */
    @Size(max = 250, message = "El nombre no debe superar los 250 caracteres")
    @XmlElement(name = "FirstName", namespace = UblNamespacesConstant.CBC)
    private String firstName;

    /**
     * Apellidos del conductor
     */
    @Size(max = 250, message = "El apellido no debe superar los 250 caracteres")
    @XmlElement(name = "FamilyName", namespace = UblNamespacesConstant.CBC)
    private String familyName;

    /**
     * Tipo de conductor (Principal/Secundario)
     */
    @Pattern(regexp = "Principal|Secundario", message = "El tipo de conductor debe ser 'Principal' o 'Secundario'")
    @XmlElement(name = "JobTitle", namespace = UblNamespacesConstant.CBC)
    private String jobTitle;

    /**
     * Licencia de conducir (IdentityDocumentReference)
     */
    @XmlElement(name = "IdentityDocumentReference", namespace = UblNamespacesConstant.CAC)
    private IdentityDocumentReference identityDocumentReference;

} 
package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.PartyIdentificationId;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Información del conductor para documentos UBL.
 * 
 * <p>
 * Representa los datos del conductor responsable del vehículo de transporte,
 * incluyendo identificación personal, nombres, apellidos y licencia de
 * conducir.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DriverPerson {
    /**
     * Identificación del conductor.
     * 
     * <p>
     * Número de documento de identidad del conductor (DNI, pasaporte, etc.)
     * junto con el esquema utilizado para su validación.
     * </p>
     */
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private PartyIdentificationId id = new PartyIdentificationId();

    /**
     * Nombres del conductor.
     * 
     * <p>
     * Nombres de pila del conductor responsable del transporte.
     * </p>
     */
    @XmlElement(name = "FirstName", namespace = UblNamespacesConstant.CBC)
    private String firstName;

    /**
     * Apellidos del conductor.
     * 
     * <p>
     * Apellidos del conductor responsable del transporte.
     * </p>
     */
    @XmlElement(name = "FamilyName", namespace = UblNamespacesConstant.CBC)
    private String familyName;

    /**
     * Tipo o categoría del conductor.
     * 
     * <p>
     * Especifica si el conductor es principal, secundario o tiene alguna
     * categoría especial según las regulaciones de transporte.
     * </p>
     */
    @XmlElement(name = "JobTitle", namespace = UblNamespacesConstant.CBC)
    private String jobTitle;

    /**
     * Referencia a la licencia de conducir.
     * 
     * <p>
     * Información sobre la licencia de conducir del conductor, incluyendo
     * número de licencia y tipo de categoría.
     * </p>
     */
    @XmlElement(name = "IdentityDocumentReference", namespace = UblNamespacesConstant.CAC)
    private IdentityDocumentReference identityDocumentReference = new IdentityDocumentReference();

}
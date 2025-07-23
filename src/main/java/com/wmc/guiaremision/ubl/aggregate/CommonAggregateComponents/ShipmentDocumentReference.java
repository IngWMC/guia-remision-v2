package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import lombok.Data;

/**
 * Referencia de documento de envío (ShipmentDocumentReference) para UBL/SUNAT
 * Incluye número de autorización y entidad emisora (schemeID)
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentDocumentReference {
    /**
     * Número de autorización (an..50, obligatorio)
     * OBSERV 4406, 3356
     */
    @NotBlank(message = "El número de autorización es obligatorio")
    @Size(min = 3, max = 50, message = "El número de autorización debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^(?!\\s*$)[^\n\r\t]*$", message = "El número de autorización no debe contener solo espacios ni caracteres de control")
    @XmlElement(name = "ID")
    private String id;

    /**
     * Entidad emisora (schemeID, Catálogo D-37, usar enum)
     */
    @XmlAttribute(name = "schemeID")
    private EntidadAutorizadoraEnum schemeID;

    /**
     * Nombre del esquema (schemeName, obligatorio 'Entidad Autorizadora')
     */
    @XmlAttribute(name = "schemeName")
    private String schemeName = "Entidad Autorizadora";

    /**
     * Agencia del esquema (schemeAgencyName, obligatorio 'PE:SUNAT')
     */
    @XmlAttribute(name = "schemeAgencyName")
    private String schemeAgencyName = "PE:SUNAT";
} 
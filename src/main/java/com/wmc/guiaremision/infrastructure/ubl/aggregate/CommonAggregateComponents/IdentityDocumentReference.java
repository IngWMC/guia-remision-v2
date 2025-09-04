package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Referencia de documento de identidad (IdentityDocumentReference) para
 * UBL/SUNAT
 * Incluye número de licencia de conducir
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IdentityDocumentReference {
    /**
     * Número de licencia de conducir (an..10, obligatorio)
     * ERROR 2572, 2573
     */
    @Size(min = 9, max = 10, message = "La licencia debe tener entre 9 y 10 caracteres")
    @Pattern(regexp = "^[A-Z0-9]{9,10}$", message = "La licencia solo permite letras mayúsculas y números")
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;
}
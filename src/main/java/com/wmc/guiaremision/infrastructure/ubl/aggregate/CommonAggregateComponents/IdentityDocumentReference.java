package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
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
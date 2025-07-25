package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import lombok.Data;

/**
 * Referencia de documento adicional (AdditionalDocumentReference) para UBL/SUNAT
 * Incluye número, tipo, código y emisor del documento relacionado
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AdditionalDocumentReference {
    /**
     * Número de documento relacionado (an..100, obligatorio)
     */
    @NotBlank(message = "El número de documento relacionado es obligatorio")
    @Size(max = 100, message = "El número de documento relacionado no debe superar los 100 caracteres")
    @XmlElement(name = "ID")
    private String id;

    /**
     * Código del tipo de documento (usar enum)
     */
    @XmlElement(name = "DocumentTypeCode")
    private String documentTypeCode;

} 
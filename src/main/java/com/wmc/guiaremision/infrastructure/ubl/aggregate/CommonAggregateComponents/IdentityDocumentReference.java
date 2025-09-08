package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Referencia a documento de identidad para documentos UBL.
 * 
 * <p>
 * Representa la información de un documento de identidad específico,
 * como licencia de conducir, pasaporte u otros documentos oficiales
 * que identifican a una persona.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IdentityDocumentReference {
    /**
     * Identificador del documento de identidad.
     * 
     * <p>
     * Número o código que identifica de manera única el documento de
     * identidad, como número de licencia de conducir, pasaporte, etc.
     * </p>
     */
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;
}
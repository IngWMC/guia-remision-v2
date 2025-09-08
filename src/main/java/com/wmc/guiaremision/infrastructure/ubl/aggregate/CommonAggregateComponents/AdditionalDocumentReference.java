package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Referencia a documento adicional para documentos UBL.
 * 
 * <p>
 * Permite referenciar documentos relacionados con la guía de remisión,
 * como facturas, pedidos, contratos u otros documentos comerciales
 * que sustentan la operación.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AdditionalDocumentReference {
    /**
     * Identificador del documento relacionado.
     * 
     * <p>
     * Número o código que identifica de manera única el documento
     * relacionado con la guía de remisión.
     * </p>
     */
    @XmlElement(name = "ID")
    private String id;

    /**
     * Código del tipo de documento relacionado.
     * 
     * <p>
     * Especifica el tipo de documento según los catálogos oficiales
     * de SUNAT (factura, pedido, contrato, etc.).
     * </p>
     */
    @XmlElement(name = "DocumentTypeCode")
    private String documentTypeCode;

}
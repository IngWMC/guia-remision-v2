package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Referencia a documento de envío para documentos UBL.
 * 
 * <p>
 * Representa la información de un documento relacionado con el envío,
 * como autorizaciones, permisos o documentos de transporte que sustentan
 * la operación de traslado de mercancías.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentDocumentReference {
    /**
     * Identificador del documento de envío.
     * 
     * <p>
     * Número o código que identifica de manera única el documento
     * relacionado con el envío, como número de autorización o permiso.
     * </p>
     */
    @XmlElement(name = "ID")
    private String id;

    /**
     * Código de la entidad emisora del documento.
     * 
     * <p>
     * Identifica la entidad que emitió el documento según el catálogo
     * oficial de entidades autorizadoras de SUNAT.
     * </p>
     */
    @XmlAttribute(name = "schemeID")
    private EntidadAutorizadoraEnum schemeID;

    /**
     * Nombre del esquema de identificación.
     * 
     * <p>
     * Especifica el tipo de esquema utilizado para identificar
     * la entidad emisora del documento.
     * </p>
     */
    @XmlAttribute(name = "schemeName")
    private String schemeName = "Entidad Autorizadora";

    /**
     * Agencia que mantiene el esquema de identificación.
     * 
     * <p>
     * Identifica la agencia oficial que mantiene y administra
     * el esquema de identificación utilizado.
     * </p>
     */
    @XmlAttribute(name = "schemeAgencyName")
    private String schemeAgencyName = "PE:SUNAT";
}
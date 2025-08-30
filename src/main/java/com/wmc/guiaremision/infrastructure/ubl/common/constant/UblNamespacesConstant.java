package com.wmc.guiaremision.infrastructure.ubl.common.constant;

/**
 * Constantes para los namespaces UBL utilizados en documentos electrónicos
 * SUNAT
 */
public class UblNamespacesConstant {

    // Namespaces principales
    public static final String DESPATCH_ADVICE = "urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2";

    // Namespaces comunes
    public static final String CAC = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
    public static final String CBC = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
    public static final String DS = "http://www.w3.org/2000/09/xmldsig#";
    public static final String EXT = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2";
    public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XSD = "http://www.w3.org/2001/XMLSchema";

    public static final String AR = "urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2";
    public static final String NODO_RESPONSE_CODE = "/ar:ApplicationResponse/cac:DocumentResponse/cac:Response/cbc:ResponseCode";
    public static final String NODO_DESCRIPTION = "/ar:ApplicationResponse/cac:DocumentResponse/cac:Response/cbc:Description";
    public static final String NODO_DOCUMENT_REFERENCE = "/ar:ApplicationResponse/cac:DocumentResponse/cac:DocumentReference/cbc:DocumentDescription";
    public static final String NODO_ID = "/ar:ApplicationResponse/cbc:ID";
    public static final String NODO_NOTE = "/ar:ApplicationResponse/cbc:Note";
}
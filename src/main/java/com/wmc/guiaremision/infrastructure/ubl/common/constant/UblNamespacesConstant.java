package com.wmc.guiaremision.infrastructure.ubl.common.constant;

/**
 * Constantes para los namespaces UBL utilizados en documentos electrónicos
 * SUNAT.
 * 
 * <p>
 * Define todos los namespaces XML requeridos para la generación de documentos
 * UBL según la especificación oficial de SUNAT.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
public class UblNamespacesConstant {

    /** Namespace principal para documentos DespatchAdvice. */
    public static final String DESPATCH_ADVICE = "urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2";

    /** Namespace para componentes agregados comunes. */
    public static final String CAC = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";

    /** Namespace para componentes básicos comunes. */
    public static final String CBC = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";

    /** Namespace para firmas digitales. */
    public static final String DS = "http://www.w3.org/2000/09/xmldsig#";

    /** Namespace para extensiones UBL. */
    public static final String EXT = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2";

    /** Namespace para instancias XML Schema. */
    public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";

    /** Namespace para XML Schema. */
    public static final String XSD = "http://www.w3.org/2001/XMLSchema";

    /** Namespace para respuestas de aplicación. */
    public static final String AR = "urn:oasis:names:specification:ubl:schema:xsd:ApplicationResponse-2";

    /** XPath para código de respuesta en ApplicationResponse. */
    public static final String NODO_RESPONSE_CODE = "/ar:ApplicationResponse/cac:DocumentResponse/cac:Response/cbc:ResponseCode";

    /** XPath para descripción en ApplicationResponse. */
    public static final String NODO_DESCRIPTION = "/ar:ApplicationResponse/cac:DocumentResponse/cac:Response/cbc:Description";

    /** XPath para referencia de documento en ApplicationResponse. */
    public static final String NODO_DOCUMENT_REFERENCE = "/ar:ApplicationResponse/cac:DocumentResponse/cac:DocumentReference/cbc:DocumentDescription";

    /** XPath para ID en ApplicationResponse. */
    public static final String NODO_ID = "/ar:ApplicationResponse/cbc:ID";

    /** XPath para nota en ApplicationResponse. */
    public static final String NODO_NOTE = "/ar:ApplicationResponse/cbc:Note";
}
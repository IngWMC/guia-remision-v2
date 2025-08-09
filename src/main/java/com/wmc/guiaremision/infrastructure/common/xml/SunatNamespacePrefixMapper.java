package com.wmc.guiaremision.infrastructure.common.xml;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Mapper personalizado para definir prefijos de namespace específicos de SUNAT.
 * 
 * <p>
 * Este mapper asegura que el XML generado use los prefijos correctos:
 * </p>
 * <ul>
 * <li>cac: para CommonAggregateComponents</li>
 * <li>cbc: para CommonBasicComponents</li>
 * <li>ext: para ExtensionComponents</li>
 * <li>ds: para DigitalSignature</li>
 * </ul>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
public class SunatNamespacePrefixMapper extends NamespacePrefixMapper {

    /**
     * Mapea las URIs de namespace a prefijos específicos.
     * 
     * @param namespaceUri  URI del namespace a mapear
     * @param suggestion    Sugerencia de prefijo por defecto
     * @param requirePrefix Si se requiere un prefijo
     * @return Prefijo personalizado o null para usar el por defecto
     * @since 1.0
     */
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

        // CommonAggregateComponents - cac
        if ("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2".equals(namespaceUri)) {
            return "cac";
        }

        // CommonBasicComponents - cbc
        if ("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2".equals(namespaceUri)) {
            return "cbc";
        }

        // ExtensionComponents - ext
        if ("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2".equals(namespaceUri)) {
            return "ext";
        }

        // DigitalSignature - ds
        if ("http://www.w3.org/2000/09/xmldsig#".equals(namespaceUri)) {
            return "ds";
        }

        // QualifiedDatatypes - qdt
        if ("urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2".equals(namespaceUri)) {
            return "qdt";
        }

        // UnqualifiedDataTypes - udt
        if ("urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2".equals(namespaceUri)) {
            return "udt";
        }

        // SunatAggregateComponents - sac
        if ("urn:sunat:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1".equals(namespaceUri)) {
            return "sac";
        }

        // XMLSchema-instance - xsi
        if ("http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri)) {
            return "xsi";
        }

        // DespatchAdvice namespace (sin prefijo para el elemento raíz)
        if ("urn:oasis:names:specification:ubl:schema:xsd:DespatchAdvice-2".equals(namespaceUri)) {
            return ""; // Sin prefijo para el namespace por defecto
        }

        // Para otros namespaces, usar la sugerencia por defecto
        return suggestion;
    }
}
package com.wmc.guiaremision.domain.spi;

import com.wmc.guiaremision.domain.dto.XmlDocumentResponse;
import com.wmc.guiaremision.domain.model.Dispatch;

/**
 * Puerto de salida para la generación de la estructura XML UBL de una guía de
 * remisión (Dispatch).
 * Define el contrato que debe implementar la infraestructura para construir el
 * XML UBL en memoria
 * a partir de los datos del dominio, devolviendo la respuesta con el XML
 * generado y el estado de la operación.
 */
public interface XmlGeneratorPort {
    /**
     * Genera la estructura XML UBL para la guía de remisión.
     *
     * @param dispatch Objeto de dominio con los datos de la guía.
     * @return DocumentResponse con el XML generado y el estado de la operación.
     */
    XmlDocumentResponse generateDispatchXml(Dispatch dispatch);
}
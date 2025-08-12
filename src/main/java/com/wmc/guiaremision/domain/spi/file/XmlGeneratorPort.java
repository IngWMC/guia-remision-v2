package com.wmc.guiaremision.domain.spi.file;

import com.wmc.guiaremision.domain.model.Dispatch;

/**
 * Puerto de salida para la generación de la estructura XML UBL de una guía de
 * remisión (Dispatch).
 * Define el contrato que debe implementar la infraestructura para construir el
 * XML UBL en memoria a partir de los datos del dominio, devolviendo la respuesta
 * con el XML generado.
 */
public interface XmlGeneratorPort {
    /**
     * Genera la estructura XML UBL para la guía de remisión.
     *
     * @param dispatch Objeto de dominio con los datos de la guía.
     * @return Una cadena de texto del XML generado.
     */
    String generateDispatchXml(Dispatch dispatch);
}
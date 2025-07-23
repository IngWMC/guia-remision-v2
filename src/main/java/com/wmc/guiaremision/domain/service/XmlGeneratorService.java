package com.wmc.guiaremision.domain.service;

import com.wmc.guiaremision.domain.model.Dispatch;

/**
 * Contrato para la generación de la estructura XML UBL de una guía de remisión (Dispatch).
 * La implementación debe construir el XML UBL en memoria y asociarlo al objeto Dispatch (por ejemplo, como String o como archivo temporal).
 */
public interface XmlGeneratorService {
    /**
     * Genera la estructura XML UBL para la guía de remisión.
     * @param dispatch Objeto de dominio con los datos de la guía.
     * @return Dispatch con el XML generado asociado (por ejemplo, en un campo xmlData).
     */
    Dispatch generateXml(Dispatch dispatch);
} 
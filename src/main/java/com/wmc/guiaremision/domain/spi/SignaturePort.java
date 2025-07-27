package com.wmc.guiaremision.domain.spi;

public interface SignaturePort {
    /**
     * Firma digitalmente el contenido XML de la guía de remisión.
     *
     * @param xmlContent El contenido XML a firmar.
     * @return El contenido XML firmado.
     */
    String signXml(String xmlContent);
}

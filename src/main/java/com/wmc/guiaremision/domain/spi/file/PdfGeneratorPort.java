package com.wmc.guiaremision.domain.spi.file;

import com.wmc.guiaremision.domain.model.Dispatch;

public interface PdfGeneratorPort {
    /**
     * Genera el PDF de la guía de remisión
     *
     * @param dispatch Datos de la guía de remisión
     * @return El PDF en formato base64
     */
    String generatePdf(Dispatch dispatch);
}
package com.wmc.guiaremision.domain.spi.file;

import com.wmc.guiaremision.domain.model.Dispatch;

/**
 * Puerto para la generación de documentos PDF.
 * <p>
 * Esta interfaz define el contrato para la generación de archivos PDF, permitiendo
 * la conversión de datos del dominio a documentos formateados.
 * </p>
 */
public interface PdfGeneratorPort {
    /**
     * Genera el PDF de la guía de remisión
     *
     * @param dispatch Datos de la guía de remisión
     * @param logoPath Ruta donde se encuentra almacenado el logo de la empresa
     * @param logoName Nombre del archivo del logo a incluir en el PDF
     * @return El PDF en formato base64
     */
    String generatePdf(Dispatch dispatch, String logoPath, String logoName);
}
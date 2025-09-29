package com.wmc.guiaremision.application.service;

/**
 * Servicio para descargar archivos relacionados a una petición.
 * <p>Proporciona métodos para obtener el contenido binario de archivos
 * según un identificador de solicitud y un tipo de archivo.</p>
 */
public interface FileService {

    /**
     * Descarga el contenido del archivo solicitado.
     *
     * @param requestId identificador de la solicitud
     * @param fileType  tipo de archivo (por ejemplo: pdf, xml)
     * @return arreglo de bytes con el contenido del archivo
     */
    byte[] download(String requestId, String fileType);
}

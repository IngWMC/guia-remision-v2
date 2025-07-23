package com.wmc.guiaremision.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * DTO para respuesta de generación de factura electrónica
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaFacturaDto {
    
    private String numeroDocumento;
    private String ticket;
    private String estado;
    private String mensaje;
    private String pdfUrl;
    private String xmlUrl;
    private String cdrUrl;
    private String codigoRespuesta;
    private String descripcionRespuesta;
} 
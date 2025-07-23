package com.wmc.guiaremision.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para datos de factura electrónica
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaDto {
    
    private String numeroDocumento;
    private String rucEmisor;
    private String rucReceptor;
    private String moneda;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaVencimiento;
    private String tipoDocumento;
    private String observaciones;
    private BigDecimal totalGravado;
    private BigDecimal totalIgv;
    private BigDecimal totalDocumento;
    private List<LineaFacturaDto> lineas;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LineaFacturaDto {
        private String codigoProducto;
        private String descripcion;
        private BigDecimal cantidad;
        private String unidadMedida;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;
        private BigDecimal igv;
        private BigDecimal total;
    }
} 
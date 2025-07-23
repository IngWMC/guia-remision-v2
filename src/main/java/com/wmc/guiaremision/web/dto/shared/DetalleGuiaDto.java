package com.wmc.guiaremision.web.dto.shared;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleGuiaDto {
    private String codigo;
    private String descripcion;
    private String unidadMedida;
    private BigDecimal cantidad;
    private String codigoInternacional;
}
package com.wmc.guiaremision.infrastructure.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private String fecha;
    private String mensaje;
    private Object detalle;
}


package com.wmc.guiaremision.web.dto.shared;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class DireccionDto {
    @NotNull
    private String direccionCompleta;
    private String ubigeo;
    private String provincia;
    private String departamento;
    private String distrito;
    private String codigoPais;
}
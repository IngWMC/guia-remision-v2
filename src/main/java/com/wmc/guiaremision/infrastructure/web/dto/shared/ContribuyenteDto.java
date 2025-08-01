package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ContribuyenteDto {
    @NotNull
    private String nroDocumento;
    @NotNull
    private String tipoDocumento;
    @NotNull
    private String nombreLegal;
    private String nombreComercial;
    private String ubigeo;
    private String direccionCompleta;
    private String urbanizacion;
    private String provincia;
    private String departamento;
    private String distrito;
    private String codigoPais;
}
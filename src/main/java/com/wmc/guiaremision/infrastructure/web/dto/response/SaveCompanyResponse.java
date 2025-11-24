package com.wmc.guiaremision.infrastructure.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveCompanyResponse {

  private int codigoDistrito;
  private int codigoEmpresaPadre;
  private String tipoDocumentoIdentidad;
  private String numeroDocumentoIdentidad;
  private String razonSocial;
  private String nombreComercial;
  private String direccion;
  private String telefono;
  private String correo;

}
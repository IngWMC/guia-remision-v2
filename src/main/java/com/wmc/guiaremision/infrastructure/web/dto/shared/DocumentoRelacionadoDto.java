package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoRelacionadoDto {

  @NotBlank(message = "El tipo de documento es obligatorio")
  private String tipoDocumento;

  @NotBlank(message = "El número de documento es obligatorio")
  private String numeroDocumento;

  private String fechaEmision;
  private String serie;
  private String correlativo;
}
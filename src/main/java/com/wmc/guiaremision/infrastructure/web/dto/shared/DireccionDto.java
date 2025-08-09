package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDto {
  @NotBlank(message = "El código de ubigeo es obligatorio")
  private String ubigeo;

  @NotBlank(message = "La dirección completa es obligatoria")
  private String direccionCompleta;
}
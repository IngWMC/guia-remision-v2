package com.wmc.guiaremision.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transportista {

  @NotBlank(message = "El RUC del transportista es obligatorio")
  @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
  private String ruc;

  @NotBlank(message = "La razón social del transportista es obligatoria")
  private String razonSocial;

  private String direccion;
  private String distrito;
  private String provincia;
  private String departamento;
}
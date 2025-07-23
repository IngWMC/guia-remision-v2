package com.wmc.guiaremision.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

  @NotBlank(message = "La dirección es obligatoria")
  private String direccion;

  @NotBlank(message = "El distrito es obligatorio")
  private String distrito;

  @NotBlank(message = "La provincia es obligatoria")
  private String provincia;

  @NotBlank(message = "El departamento es obligatorio")
  private String departamento;

  private String codigoUbigeo;
}
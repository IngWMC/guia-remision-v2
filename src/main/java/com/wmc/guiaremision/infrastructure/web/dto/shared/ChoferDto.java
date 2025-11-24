package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoferDto {
  @NotBlank(message = "El tipo de documento de identidad es obligatorio.")
  private String tipoDocumentoIdentidad;

  @NotBlank(message = "El número de documento de identidad es obligatorio.")
  @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos.")
  private String numeroDocumentoIdentidad;

  @NotBlank(message = "Los nombres son obligatorios.")
  private String nombres;

  @NotBlank(message = "Los apellidos son obligatorios.")
  private String apellidos;

  @NotBlank(message = "El número de licencia es obligatorio.")
  private String numeroLicencia;
}
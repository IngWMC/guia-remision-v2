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
  @NotBlank(message = "El campo es requerido.")
  private String tipoDocumentoIdentidad;

  @NotBlank(message = "El campo es requerido.")
  @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
  private String numeroDocumentoIdentidad;

  @NotBlank(message = "El campo es requerido.")
  private String nombres;

  @NotBlank(message = "El campo es requerido.")
  private String apellidos;

  @NotBlank(message = "El campo es requerido.")
  private String numeroLicencia;
}
package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chofer {
  private String tipoDocumentoIdentidad;

  @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
  private String numeroDocumentoIdentidad;

  @NotBlank(message = "El nombre del chofer es obligatorio")
  private String nombres;

  @NotBlank(message = "El apellido del chofer es obligatorio")
  private String apellido;

  @NotBlank(message = "El número de licencia del chofer es obligatorio")
  private String numeroLicencia;
}
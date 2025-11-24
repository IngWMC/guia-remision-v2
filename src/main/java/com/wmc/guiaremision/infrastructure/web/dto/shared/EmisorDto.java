package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmisorDto {
  @NotBlank(message = "El tipo de documento de identidad es obligatorio.")
  private String tipoDocumentoIdentidad;

  @NotBlank(message = "El número de documento de identidad es obligatorio.")
  @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos.")
  private String numeroDocumentoIdentidad;

  @NotBlank(message = "La razón social es obligatoria.")
  private String razonSocial;

  @NotBlank(message = "La dirección es obligatoria.")
  private String direccion;

  @NotBlank(message = "El código de ubigeo es obligatorio.")
  private String ubigeo;

  private String distrito;
  private String provincia;
  private String departamento;
}
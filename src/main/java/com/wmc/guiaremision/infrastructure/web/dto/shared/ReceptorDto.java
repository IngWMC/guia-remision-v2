package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceptorDto {
  @NotBlank(message = "El tipo de documento de identidad del emisor es obligatorio")
  private String tipoDocumentoIdentidad;

  @NotBlank(message = "El número de documento de identidad del emisor es obligatorio")
  @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
  private String numeroDocumentoIdentidad;

  @NotBlank(message = "La razón social del emisor es obligatoria")
  private String razonSocial;

  @NotBlank(message = "La dirección del emisor es obligatoria")
  private String direccion;

  @NotBlank(message = "El código de ubigeo del emisor es obligatorio")
  private String ubigeo;

  private String distrito;
  private String provincia;
  private String departamento;
}
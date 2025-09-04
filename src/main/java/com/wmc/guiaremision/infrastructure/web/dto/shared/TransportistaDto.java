package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportistaDto {
  private String tipoDocumentoIdentidad;

  @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
  private String numeroDocumentoIdentidad;

  @NotBlank(message = "La razón social del transportista es obligatoria")
  private String razonSocial;

  private String numeroMtc;
}
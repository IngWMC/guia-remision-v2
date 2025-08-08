package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDto {

  @NotBlank(message = "El número de placa del vehículo es requerida.")
  private String numeroPlaca;

  private String marca;
  private String modelo;
  private String color;
  private String anio;
}
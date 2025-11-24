package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDto {

  @NotBlank(message = "El número de placa del vehículo es obligatorio.")
  private String numeroPlaca;

  private String marca;
  private String modelo;
  private String color;
  private String anio;
}
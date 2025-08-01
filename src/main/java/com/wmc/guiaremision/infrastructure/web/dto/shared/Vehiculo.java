package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

  @NotBlank(message = "La placa del vehículo es obligatoria")
  @Pattern(regexp = "[A-Z]{3}-\\d{3}|[A-Z]{3}-\\d{4}", message = "Formato de placa inválido")
  private String placa;

  private String marca;
  private String modelo;
  private String color;
  private String anio;
}
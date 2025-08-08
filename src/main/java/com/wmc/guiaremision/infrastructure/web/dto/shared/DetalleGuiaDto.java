package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleGuiaDto {
  @NotNull(message = "El campo es requerido")
  @Positive(message = "La cantidad debe ser mayor a 0")
  private Integer codigoItem;

  @NotBlank(message = "El campo es requerido")
  private String codigoProducto;

  @NotBlank(message = "El campo es requerido")
  private String descripcion;

  @NotBlank(message = "El campo es requerido")
  private String unidadMedida;

  @NotNull(message = "El campo es requerido")
  @Positive(message = "La cantidad debe ser mayor a 0")
  private Integer cantidad;
}
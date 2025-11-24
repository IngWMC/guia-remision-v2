package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleGuiaDto {
  @NotNull(message = "El código del item es obligatorio.")
  @Positive(message = "El código del item debe ser mayor a 0.")
  private Integer codigoItem;

  @NotBlank(message = "El código del producto es obligatorio.")
  private String codigoProducto;

  @NotBlank(message = "La descripción es obligatoria.")
  private String descripcion;

  @NotBlank(message = "La unidad de medida es obligatoria.")
  private String unidadMedida;

  @NotNull(message = "La cantidad es obligatoria.")
  @Positive(message = "La cantidad debe ser mayor a 0.")
  private Integer cantidad;
}
package com.wmc.guiaremision.infrastructure.web.dto.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleGuia {

  @NotBlank(message = "La descripción del detalle es obligatoria")
  private String descripcion;

  @NotNull(message = "La cantidad es obligatoria")
  @Positive(message = "La cantidad debe ser mayor a 0")
  private Integer cantidad;

  private String unidadMedida;

  @Positive(message = "El peso debe ser mayor a 0")
  private BigDecimal peso;

  private String codigoProducto;
  private String codigoSunat;
}
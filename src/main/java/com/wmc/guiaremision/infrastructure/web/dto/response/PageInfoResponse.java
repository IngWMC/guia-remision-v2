package com.wmc.guiaremision.infrastructure.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResponse {
  private int paginaActual;
  private int elementosPorPagina;
  private long totalElementos;
  private int totalPaginas;
  private boolean tieneSiguiente;
  private boolean tieneAnterior;
}

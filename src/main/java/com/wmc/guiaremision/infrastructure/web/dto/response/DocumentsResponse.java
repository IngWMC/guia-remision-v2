package com.wmc.guiaremision.infrastructure.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wmc.guiaremision.application.dto.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsResponse {
  private String serieDocumento;
  private String numeroDocumento;
  private String tipoDocumentoDescripcion;
  private String fechaEmision;
  private String fechaTraslado;
  private String clienteRazonSocial;
  private String clienteNumeroDocumento;
  private String estadoSunat;
  private String estadoDescripcion;
  private ServiceResponse.Links links;
}

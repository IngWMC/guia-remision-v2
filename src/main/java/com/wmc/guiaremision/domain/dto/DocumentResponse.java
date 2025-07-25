package com.wmc.guiaremision.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta generada tras la creación o procesamiento de un documento
 * electrónico.
 * Incluye la trama XML sin firmar y el indicador de éxito de la operación.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
  /** Trama XML del documento electrónico sin firma digital. */
  private String unsignedXml;

  /** Indica si la operación fue exitosa. */
  @Builder.Default
  private Boolean success = true;
}

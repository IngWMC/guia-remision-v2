package com.wmc.guiaremision.shared.common.enums;

import lombok.Getter;

/**
 * Enum que representa los enlaces para la descarga de archivos relacionados con
 * las guías de remisión.
 * <p>
 * Cada tipo de archivo (XML, PDF, CDR) tiene una URL asociada que incluye un
 * marcador de posición para el ID de la guía de remisión.
 */
@Getter
public enum LinksFileEnum {
  XML("/api/v1/files/xml/dispatch/{0}/download"),
  PDF("/api/v1/files/pdf/dispatch/{0}/download"),
  CDR("/api/v1/files/cdr/dispatch/{0}/download");

  private final String urlFile;

  LinksFileEnum(String urlFile) {
    this.urlFile = urlFile;
  }
}

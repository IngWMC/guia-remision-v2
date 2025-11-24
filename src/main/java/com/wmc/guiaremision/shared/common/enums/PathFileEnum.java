package com.wmc.guiaremision.shared.common.enums;

import lombok.Getter;

/**
 * Enum que define las rutas de almacenamiento de archivos para una empresa.
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
@Getter
public enum PathFileEnum {
  PATH_XML_WITHOUT_SIGNATURE("/{0}/xml/sin_firmar/"),
  PATH_XML_WITH_SIGNATURE("/{0}/xml/firmado/"),
  PATH_PDF("/{0}/pdf/"),
  PATH_CDR("/{0}/cdr/"),
  PATH_CERTIFICATE("/{0}/certificado/"),
  PATH_LOGO("/{0}/logo/");

  private final String path;

  PathFileEnum(String path) {
    this.path = path;
  }
}

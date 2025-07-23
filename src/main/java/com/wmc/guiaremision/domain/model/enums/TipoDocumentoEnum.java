package com.wmc.guiaremision.domain.model.enums;

/**
 * Enum que representa los tipos de documento válidos para guías de remisión
 */
public enum TipoDocumentoEnum {

  GUIA_REMISION_REMITENTE("09", "Guía de Remisión Remitente"),
  GUIA_REMISION_TRANSPORTISTA("31", "Guía de Remisión Transportista"),
  GUIA_REMISION_OPERADOR_MAYORISTA("32", "Guía de Remisión Operador Mayorista");

  private final String codigo;
  private final String descripcion;

  TipoDocumentoEnum(String codigo, String descripcion) {
    this.codigo = codigo;
    this.descripcion = descripcion;
  }

  public String getCodigo() {
    return codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public static TipoDocumentoEnum fromCodigo(String codigo) {
    for (TipoDocumentoEnum tipo : values()) {
      if (tipo.getCodigo().equals(codigo)) {
        return tipo;
      }
    }
    throw new IllegalArgumentException("Código de tipo de documento no válido: " + codigo);
  }

  public static boolean isValid(String codigo) {
    try {
      fromCodigo(codigo);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
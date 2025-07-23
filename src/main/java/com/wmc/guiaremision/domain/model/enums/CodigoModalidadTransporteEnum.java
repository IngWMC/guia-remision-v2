package com.wmc.guiaremision.domain.model.enums;

/**
 * Enum que representa las modalidades de transporte válidas
 */
public enum CodigoModalidadTransporteEnum {

  TRANSPORTE_PUBLICO("01", "Transporte Público"),
  TRANSPORTE_PRIVADO("02", "Transporte Privado");

  private final String codigo;
  private final String descripcion;

  CodigoModalidadTransporteEnum(String codigo, String descripcion) {
    this.codigo = codigo;
    this.descripcion = descripcion;
  }

  public String getCodigo() {
    return codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public static CodigoModalidadTransporteEnum fromCodigo(String codigo) {
    for (CodigoModalidadTransporteEnum modalidad : values()) {
      if (modalidad.getCodigo().equals(codigo)) {
        return modalidad;
      }
    }
    throw new IllegalArgumentException("Código de modalidad de transporte no válido: " + codigo);
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
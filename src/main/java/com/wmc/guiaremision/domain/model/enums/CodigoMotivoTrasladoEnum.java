package com.wmc.guiaremision.domain.model.enums;

/**
 * Enum que representa los motivos de traslado válidos
 */
public enum CodigoMotivoTrasladoEnum {

  VENTA("01", "Venta"),
  COMPRA("02", "Compra"),
  VENTA_SUJETA_A_CONFIRMACION("03", "Venta Sujeta a Confirmación"),
  TRASLADO_ENTRE_ESTABLECIMIENTOS("04", "Traslado entre Establecimientos"),
  CONSIGNACION("05", "Consignación"),
  DEVOLUCION("06", "Devolución"),
  TRASLADO_EMISOR_ITINERANTE("07", "Traslado Emisor Itinerante"),
  IMPORTACION("08", "Importación"),
  EXPORTACION("09", "Exportación"),
  OTROS("13", "Otros");

  private final String codigo;
  private final String descripcion;

  CodigoMotivoTrasladoEnum(String codigo, String descripcion) {
    this.codigo = codigo;
    this.descripcion = descripcion;
  }

  public String getCodigo() {
    return codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public static CodigoMotivoTrasladoEnum fromCodigo(String codigo) {
    for (CodigoMotivoTrasladoEnum motivo : values()) {
      if (motivo.getCodigo().equals(codigo)) {
        return motivo;
      }
    }
    throw new IllegalArgumentException("Código de motivo de traslado no válido: " + codigo);
  }

  public static boolean isValid(String codigo) {
    try {
      fromCodigo(codigo);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public boolean requiereDescripcion() {
    return "13".equals(this.codigo);
  }
}
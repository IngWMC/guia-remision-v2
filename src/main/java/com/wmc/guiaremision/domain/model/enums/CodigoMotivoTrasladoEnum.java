package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum que representa los motivos de traslado válidos en el sistema.
 * <p>
 * Cada motivo tiene un código y una descripción asociada:
 * <ul>
 *   <li>"01": Venta</li>
 *   <li>"02": Compra</li>
 *   <li>"03": Venta Sujeta a Confirmación</li>
 *   <li>"04": Traslado entre Establecimientos</li>
 *   <li>"05": Consignación</li>
 *   <li>"06": Devolución</li>
 *   <li>"07": Traslado Emisor Itinerante</li>
 *   <li>"08": Importación</li>
 *   <li>"09": Exportación</li>
 *   <li>"13": Otros</li>
 * </ul>
 * Utiliza los métodos utilitarios para validar y obtener motivos por código.
 */
@Getter
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

  public static CodigoMotivoTrasladoEnum fromCode(String codigo) {
    return Optional.ofNullable(codigo)
        .flatMap(c -> Arrays.stream(values())
            .filter(motivo -> motivo.getCodigo().equals(c))
            .findFirst())
        .orElseThrow(() -> new IllegalArgumentException("Código de motivo de traslado no válido: " + codigo));
  }

  public static boolean isValid(String codigo) {
    return Optional.ofNullable(codigo)
        .map(CodigoMotivoTrasladoEnum::fromCode)
        .isPresent();
  }
}
package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum que representa las modalidades de transporte válidas en el sistema.
 * <p>
 * Cada modalidad tiene un código y una descripción asociada:
 * <ul>
 * <li>"01": Transporte Público</li>
 * <li>"02": Transporte Privado</li>
 * </ul>
 * Utiliza los métodos utilitarios para validar y obtener modalidades por
 * código.
 */
@Getter
public enum CodigoModalidadTransporteEnum {

  TRANSPORTE_PUBLICO("01", "Transporte Público"),
  TRANSPORTE_PRIVADO("02", "Transporte Privado");

  private final String codigo;
  private final String descripcion;

  CodigoModalidadTransporteEnum(String codigo, String descripcion) {
    this.codigo = codigo;
    this.descripcion = descripcion;
  }

  public static CodigoModalidadTransporteEnum fromCode(String codigo) {
    return Optional.ofNullable(codigo)
        .flatMap(c -> Arrays.stream(values())
            .filter(modalidad -> modalidad.getCodigo().equals(c))
            .findFirst())
        .orElseThrow(() -> new IllegalArgumentException("Código de modalidad de transporte no válido: " + codigo));
  }

  public static boolean isValid(String codigo) {
    return Optional.ofNullable(codigo)
        .map(CodigoModalidadTransporteEnum::fromCode)
        .isPresent();
  }
}
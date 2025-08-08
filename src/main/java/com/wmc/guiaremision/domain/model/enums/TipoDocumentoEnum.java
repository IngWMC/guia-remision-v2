package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum que representa los tipos de documento válidos para guías de remisión
 */
@Getter
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

  public static TipoDocumentoEnum fromCode(String codigo) {
    return Optional.ofNullable(codigo)
        .flatMap(c -> Arrays.stream(values())
        .filter(tipo -> tipo.getCodigo().equals(c))
            .findFirst())
        .orElseThrow(() -> new IllegalArgumentException("Código de tipo de documento no válido: " + codigo));
  }

  public static boolean isValid(String codigo) {
    return Optional.ofNullable(codigo)
        .map(TipoDocumentoEnum::fromCode)
        .isPresent();
  }
}
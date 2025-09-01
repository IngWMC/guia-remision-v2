package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum SunatStatusEnum {
  PENDIENTE(1, "PENDIENTE"),
  ENVIADO(2, "ENVIADO"),
  ACEPTADO(3, "ACEPTADO"),
  OBSERVADO(4, "OBSERVADO"),
  RECHAZADO(5, "RECHAZADO"),
  ANULADO(6, "ANULADO"),
  ERROR(7, "ERROR");

  private final Integer code;
  private final String description;

  SunatStatusEnum(Integer code, String description) {
    this.code = code;
    this.description = description;
  }

  public static SunatStatusEnum fromCode(Integer code) {
    return Optional.ofNullable(code)
        .flatMap(c -> Arrays.stream(values())
            .filter(statusEnum -> statusEnum.getCode().equals(c))
            .findFirst())
        .orElseThrow(() -> new IllegalArgumentException("Código de estado de la sunat no válido: " + code));
  }
}

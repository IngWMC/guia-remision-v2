package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum SunatStatusEnum {
  PENDIENTE("PENDIENTE", "Pendiente de envío a la SUNAT"),
  ENVIADO("ENVIADO", "Enviado a la SUNAT"),
  ACEPTADO("ACEPTADO", "Aceptado por la SUNAT"),
  OBSERVADO("OBSERVADO", "Observado por la SUNAT"),
  RECHAZADO("RECHAZADO", "Rechazado por la SUNAT"),
  ANULADO("ANULADO", "Anulado en la SUNAT"),
  ERROR("ERROR", "Error en el envío a la SUNAT");

  private final String code;
  private final String description;

  SunatStatusEnum(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static SunatStatusEnum fromCode(String code) {
    return Optional.ofNullable(code)
        .flatMap(c -> Arrays.stream(values())
            .filter(statusEnum -> statusEnum.getCode().equals(c))
            .findFirst())
        .orElseThrow(() -> new IllegalArgumentException("Código de estado de la sunat no válido: " + code));
  }
}

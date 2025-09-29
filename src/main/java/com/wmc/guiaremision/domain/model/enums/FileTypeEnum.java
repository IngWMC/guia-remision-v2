package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum FileTypeEnum {
  PDF("pdf", ".pdf", "application/pdf"),
  XML("xml", ".xml", "application/xml"),
  CDR("cdr", ".zip", "application/zip");

  private final String code;
  private final String extension;
  private final String mimeType;

  FileTypeEnum(String code, String extension, String mimeType) {
    this.code = code;
    this.extension = extension;
    this.mimeType = mimeType;
  }

  public static FileTypeEnum fromCode(String code) {
    return Optional.ofNullable(code)
        .flatMap(c -> Arrays.stream(values())
            .filter(tipo -> tipo.getCode().equals(c))
            .findFirst())
        .orElseThrow(() -> new IllegalArgumentException("No exite el tipo de archivo con el código: " + code));
  }
}

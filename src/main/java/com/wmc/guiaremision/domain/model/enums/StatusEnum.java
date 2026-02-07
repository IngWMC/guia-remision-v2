package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
  ACTIVE("A", "Active"),
  INACTIVE("I", "Inactive");

  private final String code;
  private final String description;

  StatusEnum(String code, String description) {
    this.code = code;
    this.description = description;
  }
}

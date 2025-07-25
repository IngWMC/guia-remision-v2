package com.wmc.guiaremision.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Driver {
  private String licenseNumber;
  private String firstName;
  private String lastName;
  private String documentNumber;
  private String documentType;
}

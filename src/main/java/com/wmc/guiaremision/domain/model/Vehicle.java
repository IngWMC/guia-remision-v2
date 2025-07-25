package com.wmc.guiaremision.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Vehicle {
  private String plate;
  private String brand;
  private String model;
  private String color;
  private String year;
}

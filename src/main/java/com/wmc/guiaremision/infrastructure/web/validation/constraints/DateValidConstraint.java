package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import com.wmc.guiaremision.infrastructure.web.validation.DateValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidConstraint implements ConstraintValidator<DateValid, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty())
      return true; // Manejar valores nulos o vacíos
    try {
      LocalDate.parse(value); // Verifica si la fecha es válida
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}

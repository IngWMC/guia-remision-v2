package com.wmc.guiaremision.web.validation.constraints;

import com.wmc.guiaremision.web.validation.EnumValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class EnumValidConstraint implements ConstraintValidator<EnumValid, String> {

  private String[] acceptedValues;

  @Override
  public void initialize(EnumValid annotation) {
    acceptedValues = Arrays.stream(annotation.enumClass().getEnumConstants())
        .map(en -> {
          try {
            return en.getClass().getMethod("getCodigo").invoke(en).toString();
          } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
          }
        })
        .toArray(String[]::new);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return true;
    for (String v : acceptedValues) {
      if (v.equals(value))
        return true;
    }
    // Personaliza el mensaje de error con los valores válidos
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(
            "El valor debe ser uno de los siguientes: [" + String.join(", ", acceptedValues) + "]")
        .addConstraintViolation();
    return false;
  }
}
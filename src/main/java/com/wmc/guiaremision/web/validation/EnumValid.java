package com.wmc.guiaremision.web.validation;

import com.wmc.guiaremision.web.validation.constraints.EnumValidConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidConstraint.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {
  String message() default "El valor debe ser uno de los siguientes: {enumValues}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  Class<? extends Enum<?>> enumClass();
}

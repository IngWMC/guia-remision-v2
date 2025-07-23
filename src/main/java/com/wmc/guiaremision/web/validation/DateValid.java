package com.wmc.guiaremision.web.validation;

import com.wmc.guiaremision.web.validation.constraints.DateValidConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidConstraint.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateValid {
  String message() default "La fecha no es válida.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

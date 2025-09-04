package com.wmc.guiaremision.infrastructure.web.validation;

import com.wmc.guiaremision.infrastructure.web.validation.constraints.TransportistaConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = TransportistaConstraint.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface TransportistaValid {
  String message() default "";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

package com.wmc.guiaremision.infrastructure.web.validation;

import com.wmc.guiaremision.infrastructure.web.validation.constraints.VehiculoConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = VehiculoConstraint.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface VehiculoValid {
  String message() default "";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

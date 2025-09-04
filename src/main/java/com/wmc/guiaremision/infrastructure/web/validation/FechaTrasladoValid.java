package com.wmc.guiaremision.infrastructure.web.validation;

import com.wmc.guiaremision.infrastructure.web.validation.constraints.FechaTrasladoConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = FechaTrasladoConstraint.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface FechaTrasladoValid {
  String message() default "La fecha es obligatoria cuando el tipo documento es '31'.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

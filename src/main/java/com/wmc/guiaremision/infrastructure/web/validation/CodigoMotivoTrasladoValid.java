package com.wmc.guiaremision.infrastructure.web.validation;

import com.wmc.guiaremision.infrastructure.web.validation.constraints.CodigoMotivoTrasladoConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CodigoMotivoTrasladoConstraint.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface CodigoMotivoTrasladoValid {
  String message() default "La valor es obligatoria cuando el tipo documento es '09'.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

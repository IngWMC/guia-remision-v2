package com.wmc.guiaremision.infrastructure.web.validation;

import com.wmc.guiaremision.infrastructure.web.validation.constraints.CodigoModalidadTransporteConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = CodigoModalidadTransporteConstraint.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface CodigoModalidadTransporteValid {
  String message() default "El valor es requerido cuando el tipo documento es '09'.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

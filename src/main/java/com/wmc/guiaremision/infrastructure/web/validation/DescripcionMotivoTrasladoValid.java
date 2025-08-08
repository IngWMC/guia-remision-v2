package com.wmc.guiaremision.infrastructure.web.validation;

import com.wmc.guiaremision.infrastructure.web.validation.constraints.DescripcionMotivoTrasladoConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = DescripcionMotivoTrasladoConstraint.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface DescripcionMotivoTrasladoValid {
  String message() default "La valor es requerido cuando el motivo traslado es '13'.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

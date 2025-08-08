package com.wmc.guiaremision.infrastructure.web.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.wmc.guiaremision.infrastructure.web.validation.constraints.SerieDocumentoConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = SerieDocumentoConstraint.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface SerieDocumentoValid {
    String message() default "La serie debe comenzar con 'T' si el tipo de documento es '9', o con 'V' si el tipo de documento es '31'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

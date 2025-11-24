package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import static com.wmc.guiaremision.domain.model.enums.CodigoMotivoTrasladoEnum.OTROS;

import com.wmc.guiaremision.infrastructure.web.dto.request.GenerateGreRequest;
import com.wmc.guiaremision.infrastructure.web.validation.DescripcionMotivoTrasladoValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DescripcionMotivoTrasladoConstraint
  implements ConstraintValidator<DescripcionMotivoTrasladoValid, GenerateGreRequest> {
  @Override
  public boolean isValid(GenerateGreRequest dto, ConstraintValidatorContext context) {
    if (dto == null) return true;

    String descripcionMotivoTraslado = dto.getDescripcionMotivoTraslado();
    String codigoMotivoTraslado = dto.getCodigoMotivoTraslado();
    boolean isMotivoTraslado = OTROS.getCodigo().equals(codigoMotivoTraslado);
    boolean errorMotivoTraslado = isMotivoTraslado && descripcionMotivoTraslado == null;
    if (errorMotivoTraslado) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("descripcionMotivoTraslado")
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}

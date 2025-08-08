package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import static com.wmc.guiaremision.domain.model.enums.CodigoMotivoTrasladoEnum.OTROS;

import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.validation.DescripcionMotivoTrasladoValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescripcionMotivoTrasladoConstraint
  implements ConstraintValidator<DescripcionMotivoTrasladoValid, CrearGuiaRemisionDto> {
  @Override
  public boolean isValid(CrearGuiaRemisionDto dto, ConstraintValidatorContext context) {
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

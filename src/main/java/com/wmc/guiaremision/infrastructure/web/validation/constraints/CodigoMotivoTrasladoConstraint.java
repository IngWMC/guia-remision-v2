package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.validation.CodigoMotivoTrasladoValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum.GUIA_REMISION_REMITENTE;

public class CodigoMotivoTrasladoConstraint
  implements ConstraintValidator<CodigoMotivoTrasladoValid, CrearGuiaRemisionDto> {
  @Override
  public boolean isValid(CrearGuiaRemisionDto dto, ConstraintValidatorContext context) {
    if (dto == null) return true;

    String codigoMotivoTraslado = dto.getCodigoMotivoTraslado();
    String tipoDocumento = dto.getTipoDocumento();

    boolean isRemitente = GUIA_REMISION_REMITENTE.getCodigo().equals(tipoDocumento);
    boolean errorRemitente = isRemitente && codigoMotivoTraslado == null;
    if (errorRemitente) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("codigoMotivoTraslado")
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}

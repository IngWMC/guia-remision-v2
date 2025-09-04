package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import static com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum.GUIA_REMISION_REMITENTE;

import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.validation.CodigoModalidadTransporteValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoModalidadTransporteConstraint
  implements ConstraintValidator<CodigoModalidadTransporteValid, CrearGuiaRemisionDto> {
  @Override
  public boolean isValid(CrearGuiaRemisionDto dto, ConstraintValidatorContext context) {
    if (dto == null) return true;
    String codigoModalidadTransporte = dto.getCodigoModalidadTransporte();
    String tipoDocumento = dto.getTipoDocumento();

    boolean isRemitente = GUIA_REMISION_REMITENTE.getCodigo().equals(tipoDocumento);
    boolean errorRemitente = isRemitente && codigoModalidadTransporte == null;
    if (errorRemitente) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("codigoModalidadTransporte")
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}

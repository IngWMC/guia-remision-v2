package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import static com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum.GUIA_REMISION_REMITENTE;
import static com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum.GUIA_REMISION_TRANSPORTISTA;

import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.validation.SerieDocumentoValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SerieDocumentoConstraint
    implements ConstraintValidator<SerieDocumentoValid, CrearGuiaRemisionDto> {
  @Override
  public boolean isValid(CrearGuiaRemisionDto dto, ConstraintValidatorContext context) {
    if (dto == null) return true;

    String serieDocumento = dto.getSerieDocumento();
    String tipoDocumento = dto.getTipoDocumento();
    if (serieDocumento == null || tipoDocumento == null) return true;

    boolean isRemitente = GUIA_REMISION_REMITENTE.getCodigo().equals(tipoDocumento);
    boolean isTransportista = GUIA_REMISION_TRANSPORTISTA.getCodigo().equals(tipoDocumento);
    boolean errorRemitente = isRemitente && !serieDocumento.startsWith("T");
    boolean errorTransportista = isTransportista && !serieDocumento.startsWith("V");
    if (errorRemitente || errorTransportista) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("serieDocumento")
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}

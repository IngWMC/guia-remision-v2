package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.dto.shared.TransportistaDto;
import com.wmc.guiaremision.infrastructure.web.validation.TransportistaValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransportistaConstraint implements ConstraintValidator<TransportistaValid, CrearGuiaRemisionDto> {
  @Override
  public boolean isValid(CrearGuiaRemisionDto dto, ConstraintValidatorContext context) {
    if (dto == null) return true;
    TransportistaDto transportista = dto.getTransportista();
    String tipoDocumento = dto.getTipoDocumento();
    String codigoModalidadTransporte = dto.getCodigoModalidadTransporte();

    boolean isGreSender = TipoDocumentoEnum.GUIA_REMISION_REMITENTE.getCodigo().equals(tipoDocumento);
    boolean isTransportPublic = CodigoModalidadTransporteEnum.TRANSPORTE_PUBLICO.getCodigo().equals(codigoModalidadTransporte);
    boolean error = isGreSender && isTransportPublic && transportista == null;
    if (error) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("El trasportista es requerido.")
          .addPropertyNode("transportista")
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}

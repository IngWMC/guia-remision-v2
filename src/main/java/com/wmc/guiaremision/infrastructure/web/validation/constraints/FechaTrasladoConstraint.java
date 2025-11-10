package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import static com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum.GUIA_REMISION_REMITENTE;
import static com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum.GUIA_REMISION_TRANSPORTISTA;
import static com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum.TRANSPORTE_PRIVADO;

import com.wmc.guiaremision.infrastructure.web.dto.request.GenerateGreRequest;
import com.wmc.guiaremision.infrastructure.web.validation.FechaTrasladoValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechaTrasladoConstraint
    implements ConstraintValidator<FechaTrasladoValid, GenerateGreRequest> {
  @Override
  public boolean isValid(GenerateGreRequest dto, ConstraintValidatorContext context) {
    if (dto == null) return true;
    String fechaTraslado = dto.getFechaTraslado();
    String tipoDocumento = dto.getTipoDocumento();
    String codigoModalidadTransporte = dto.getCodigoModalidadTransporte();

    boolean isTransportista = GUIA_REMISION_TRANSPORTISTA.getCodigo().equals(tipoDocumento);
    boolean errorTransportista = isTransportista && fechaTraslado == null;
    if (errorTransportista) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
          .addPropertyNode("fechaTraslado")
          .addConstraintViolation();
      return false;
    }

    boolean isRemitente = GUIA_REMISION_REMITENTE.getCodigo().equals(tipoDocumento);
    boolean errorRemitente = isRemitente && TRANSPORTE_PRIVADO.getCodigo().equals(codigoModalidadTransporte) && fechaTraslado == null;
    if (errorRemitente) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("La fecha es requerido cuando la modalidad de transporte es '02'.")
          .addPropertyNode("fechaTraslado")
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}

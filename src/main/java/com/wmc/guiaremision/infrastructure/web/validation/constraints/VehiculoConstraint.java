package com.wmc.guiaremision.infrastructure.web.validation.constraints;

import com.wmc.guiaremision.domain.model.enums.CodigoModalidadTransporteEnum;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.infrastructure.web.dto.request.GenerateGreRequest;
import com.wmc.guiaremision.infrastructure.web.dto.shared.VehiculoDto;
import com.wmc.guiaremision.infrastructure.web.validation.VehiculoValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public class VehiculoConstraint implements ConstraintValidator<VehiculoValid, GenerateGreRequest> {
  @Override
  public boolean isValid(GenerateGreRequest dto, ConstraintValidatorContext context) {
    if (dto == null) return true;
    VehiculoDto vehiculo = dto.getVehiculo();
    String tipoDocumento = dto.getTipoDocumento();
    String codigoModalidadTransporte = dto.getCodigoModalidadTransporte();

    boolean isGreSender = TipoDocumentoEnum.GUIA_REMISION_REMITENTE.getCodigo().equals(tipoDocumento);
    boolean isGreCarrier = TipoDocumentoEnum.GUIA_REMISION_TRANSPORTISTA.getCodigo().equals(tipoDocumento);
    boolean isTransportPrivate = CodigoModalidadTransporteEnum.TRANSPORTE_PRIVADO.getCodigo().equals(codigoModalidadTransporte);
    boolean error = Stream.of(
        isGreSender && isTransportPrivate && vehiculo == null,
        isGreCarrier && vehiculo == null
    ).anyMatch(Boolean::booleanValue);

    if (error) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("El vehículo es requerido.")
          .addPropertyNode("vehiculo")
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}

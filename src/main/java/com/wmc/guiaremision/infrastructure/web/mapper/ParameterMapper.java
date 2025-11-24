package com.wmc.guiaremision.infrastructure.web.mapper;

import com.wmc.guiaremision.domain.entity.ParameterEntity;
import com.wmc.guiaremision.infrastructure.web.dto.request.SaveParameterRequest;
import com.wmc.guiaremision.infrastructure.web.dto.response.SaveParameterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper para convertir objetos relacionados con parámetros entre DTOs y
 * entidades.
 * <p>
 * Utiliza MapStruct para generar automáticamente el código de conversión entre
 * los DTOs de entrada ({@link SaveParameterRequest}), las entidades del dominio
 * ({@link ParameterEntity}) y los DTOs de salida
 * ({@link SaveParameterResponse}).
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParameterMapper {

  /**
   * Convierte un {@link SaveParameterRequest} a una entidad
   * {@link ParameterEntity}.
   * <p>
   * <strong>Nota:</strong> El campo {@code companyId} debe ser establecido
   * manualmente en el servicio después de buscar la empresa por su RUC.
   * </p>
   *
   * @param request DTO con los datos del parámetro a guardar
   * @return entidad ParameterEntity mapeada (sin companyId)
   */
  @Mapping(target = "companyId", ignore = true)
  @Mapping(target = "logoName", source = "nombreLogo")
  @Mapping(target = "certificateName", source = "nombreCertificado")
  @Mapping(target = "certificatePassword", source = "contrasenaCertificado")
  ParameterEntity mapperSaveParameterRequestToParameterEntity(SaveParameterRequest request);

  /**
   * Convierte una entidad {@link ParameterEntity} a un
   * {@link SaveParameterResponse}.
   *
   * @param entity entidad ParameterEntity con los datos del parámetro
   * @return DTO SaveParameterResponse mapeado
   */
  @Mapping(target = "codigoParametro", source = "entity.parameterId")
  @Mapping(target = "rucEmpresa", source = "rucEmpresa")
  @Mapping(target = "nombreLogo", source = "entity.logoName")
  @Mapping(target = "nombreCertificado", source = "entity.certificateName")
  SaveParameterResponse mapperParameterEntityToSaveParameterResponse(ParameterEntity entity, String rucEmpresa);

}

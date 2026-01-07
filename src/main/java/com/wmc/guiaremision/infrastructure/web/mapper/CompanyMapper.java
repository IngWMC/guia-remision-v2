package com.wmc.guiaremision.infrastructure.web.mapper;

import com.wmc.guiaremision.application.dto.CompanyFindAllRequest;
import com.wmc.guiaremision.application.dto.DocumentFindAllRequest;
import com.wmc.guiaremision.application.dto.FindAllResponse;
import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoIdentidadEnum;
import com.wmc.guiaremision.infrastructure.web.dto.request.CompanyQueryParamRequest;
import com.wmc.guiaremision.infrastructure.web.dto.request.DocumentQueryParamRequest;
import com.wmc.guiaremision.infrastructure.web.dto.request.SaveCompanyRequest;
import com.wmc.guiaremision.infrastructure.web.dto.response.CompaniesResponse;
import com.wmc.guiaremision.infrastructure.web.dto.response.DocumentsResponse;
import com.wmc.guiaremision.infrastructure.web.dto.response.PaginationListResponse;
import com.wmc.guiaremision.infrastructure.web.dto.response.SaveCompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

/**
 * Mapper para convertir objetos relacionados con empresas entre DTOs y
 * entidades.
 * <p>
 * Este mapper utiliza MapStruct para generar automáticamente el código de
 * conversión entre los DTOs de entrada ({@link SaveCompanyRequest}) y las
 * entidades del dominio ({@link CompanyEntity}).
 * </p>
 * <p>
 * Configuración:
 * <ul>
 * <li><strong>componentModel = "spring":</strong> Genera el mapper como un
 * bean de Spring</li>
 * <li><strong>unmappedTargetPolicy = IGNORE:</strong> Ignora propiedades no
 * mapeadas explícitamente</li>
 * </ul>
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 * @see SaveCompanyRequest
 * @see CompanyEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
  /**
   * Convierte un {@link SaveCompanyRequest} a una entidad
   * {@link CompanyEntity}.
   *
   * @param request DTO con los datos de la empresa a guardar
   * @return entidad CompanyEntity mapeada
   * @throws IllegalArgumentException si el tipo de documento no es válido
   */
  @Mapping(target = "districtId", source = "codigoDistrito")
  @Mapping(target = "parentCompanyId", source = "codigoEmpresaPadre")
  @Mapping(target = "identityDocumentType", source = "tipoDocumentoIdentidad")
  @Mapping(target = "identityDocumentNumber", source = "numeroDocumentoIdentidad")
  @Mapping(target = "legalName", source = "razonSocial")
  @Mapping(target = "tradeName", source = "nombreComercial")
  @Mapping(target = "address", source = "direccion")
  @Mapping(target = "phone", source = "telefono")
  @Mapping(target = "email", source = "correo")
  @Mapping(target = "solUser", source = "usuarioSol")
  @Mapping(target = "solPassword", source = "claveSol")
  @Mapping(target = "clientId", source = "clientId")
  @Mapping(target = "clientSecret", source = "clientSecret")
  CompanyEntity mapperSaveCompanyRequestToCompanyEntity(SaveCompanyRequest request);

  /**
   * Convierte una entidad {@link CompanyEntity} a un
   * {@link SaveCompanyResponse}.
   *
   * @param entity entidad CompanyEntity con los datos de la empresa
   * @return DTO SaveCompanyResponse mapeado
   */
  @Mapping(target = "codigoDistrito", source = "districtId")
  @Mapping(target = "codigoEmpresaPadre", source = "parentCompanyId")
  @Mapping(target = "tipoDocumentoIdentidad", source = "identityDocumentType")
  @Mapping(target = "numeroDocumentoIdentidad", source = "identityDocumentNumber")
  @Mapping(target = "razonSocial", source = "legalName")
  @Mapping(target = "nombreComercial", source = "tradeName")
  @Mapping(target = "direccion", source = "address")
  @Mapping(target = "telefono", source = "phone")
  @Mapping(target = "correo", source = "email")
  SaveCompanyResponse mapperCompanyEntityToSaveCompanyResponse(CompanyEntity entity);

  @Mapping(target = "identityDocumentType", source = "identityDocumentType", qualifiedByName = "mapIdentityDocumentType")
  @Mapping(target = "identityDocumentNumber", source = "identityDocumentNumber")
  @Mapping(target = "legalName", source = "legalName")
  @Mapping(target = "page", source = "page", defaultValue = "0")
  @Mapping(target = "size", source = "size", defaultValue = "10")
  @Mapping(target = "sortBy", source = "sortBy", defaultValue = "companyId")
  @Mapping(target = "sortDir", source = "sortDir", defaultValue = "asc")
  CompanyFindAllRequest toCompanyFindAllRequest(CompanyQueryParamRequest queryParam);

  @Mapping(target = "contenido", source = "list")
  @Mapping(target = "infoPagina.paginaActual", source = "currentPage")
  @Mapping(target = "infoPagina.elementosPorPagina", source = "pageSize")
  @Mapping(target = "infoPagina.totalElementos", source = "totalElements")
  @Mapping(target = "infoPagina.totalPaginas", source = "totalPages")
  @Mapping(target = "infoPagina.tieneSiguiente", source = "hasNext")
  @Mapping(target = "infoPagina.tieneAnterior", source = "hasPrevious")
  PaginationListResponse<CompaniesResponse> toCompanyFindAllResponse(
      FindAllResponse<CompanyEntity> findAllResponse);

  @Mapping(target = "tipoDocumentoIdentidad", source = "identityDocumentType", qualifiedByName = "mapIdentityDocumentType")
  @Mapping(target = "numeroDocumentoIdentidad", source = "identityDocumentNumber")
  @Mapping(target = "razonSocial", source = "legalName")
  @Mapping(target = "nombreComercial", source = "tradeName")
  @Mapping(target = "direccion", source = "address")
  @Mapping(target = "estado", source = "status")
  CompaniesResponse toCompaniesResponse(CompanyEntity entity);

  @Named("mapIdentityDocumentType")
  default String mapIdentityDocumentType(String identityDocumentType) {
    return Optional.ofNullable(identityDocumentType)
        .map(TipoDocumentoIdentidadEnum::getDescripcionCortaByCodigo)
        .orElse(null);
  }
}

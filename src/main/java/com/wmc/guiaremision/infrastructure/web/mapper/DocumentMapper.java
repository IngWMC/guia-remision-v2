package com.wmc.guiaremision.infrastructure.web.mapper;

import com.wmc.guiaremision.application.dto.DocumentFindAllRequest;
import com.wmc.guiaremision.application.dto.FindAllResponse;
import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.infrastructure.web.dto.request.DocumentQueryParamRequest;
import com.wmc.guiaremision.infrastructure.web.dto.response.DocumentsResponse;
import com.wmc.guiaremision.infrastructure.web.dto.response.PaginationListResponse;
import com.wmc.guiaremision.shared.common.Constant;
import com.wmc.guiaremision.shared.common.Util;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {

  @Mapping(target = "documentCode", source = "queryParam", qualifiedByName = "buildDocumentCode")
  @Mapping(target = "startDate", source = "startDate", qualifiedByName = "convertToStartDateTime")
  @Mapping(target = "endDate", source = "endDate", qualifiedByName = "convertToEndDateTime")
  @Mapping(target = "statusSunat", source = "statusSunat")
  @Mapping(target = "page", source = "page", defaultValue = "0")
  @Mapping(target = "size", source = "size", defaultValue = "10")
  @Mapping(target = "sortBy", source = "sortBy", defaultValue = "documentId")
  @Mapping(target = "sortDir", source = "sortDir", defaultValue = "asc")
  DocumentFindAllRequest toDocumentFindAllRequest(DocumentQueryParamRequest queryParam);

  @Mapping(target = "contenido", source = "list")
  @Mapping(target = "infoPagina.paginaActual", source = "currentPage")
  @Mapping(target = "infoPagina.elementosPorPagina", source = "pageSize")
  @Mapping(target = "infoPagina.totalElementos", source = "totalElements")
  @Mapping(target = "infoPagina.totalPaginas", source = "totalPages")
  @Mapping(target = "infoPagina.tieneSiguiente", source = "hasNext")
  @Mapping(target = "infoPagina.tieneAnterior", source = "hasPrevious")
  PaginationListResponse<DocumentsResponse> toDocumentFindAllResponse(
      FindAllResponse<Dispatch> findAllResponse);

  @Mapping(target = "serieDocumento", source = "documentSeries")
  @Mapping(target = "numeroDocumento", source = "documentNumber")
  @Mapping(target = "tipoDocumentoDescripcion", source = "documentType", qualifiedByName = "mapDocumentTypeDescription")
  @Mapping(target = "fechaEmision", source = "issueDate")
  @Mapping(target = "fechaTraslado", source = "transferDate")
  @Mapping(target = "clienteRazonSocial", source = "receiver.legalName")
  @Mapping(target = "clienteNumeroDocumento", source = "receiver.identityDocumentNumber")
  @Mapping(target = "estadoSunat", source = "sunatStatus")
  @Mapping(target = "links", source = "requestId", qualifiedByName = "mapLinks")
  DocumentsResponse toDocumentsResponse(Dispatch dispatch);

  @Named("buildDocumentCode")
  default String buildDocumentCode(DocumentQueryParamRequest queryParam) {
    String serie = Optional.ofNullable(queryParam.getDocumentSerie())
        .filter(s -> !s.isBlank())
        .orElse(null);

    String numero = Optional.ofNullable(queryParam.getDocumentNumber())
        .filter(n -> !n.isBlank())
        .map(n -> String.valueOf(Integer.parseInt(n)))
        .orElse(null);

    return Optional.ofNullable(serie)
        .map(s -> Optional.ofNullable(numero)
            .map(n -> s.concat(Constant.DASH).concat(n))
            .orElse(s))
        .or(() -> Optional.ofNullable(numero))
        .orElse(null);
  }

  @Named("convertToStartDateTime")
  default LocalDateTime convertToStartDateTime(LocalDate date) {
    return Optional.ofNullable(date)
        .map(LocalDate::atStartOfDay)
        .orElse(null);
  }

  @Named("convertToEndDateTime")
  default LocalDateTime convertToEndDateTime(LocalDate date) {
    return Optional.ofNullable(date)
        .map(d -> d.atTime(23, 59, 59))
        .orElse(null);
  }

  @Named("mapDocumentTypeDescription")
  default String mapDocumentTypeDescription(TipoDocumentoEnum documentType) {
    return Optional.ofNullable(documentType)
        .map(TipoDocumentoEnum::getDescripcion)
        .orElse(null);
  }

  @Named("mapLinks")
  default ServiceResponse.Links mapLinks(String requestId) {
    return Util.buildFileUrl(requestId);
  }
}

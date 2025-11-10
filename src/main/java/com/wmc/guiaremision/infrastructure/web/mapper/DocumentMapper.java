package com.wmc.guiaremision.infrastructure.web.mapper;

import com.wmc.guiaremision.application.dto.DocumentFindAllRequest;
import com.wmc.guiaremision.application.dto.DocumentFindAllResponse;
import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.model.enums.TipoDocumentoEnum;
import com.wmc.guiaremision.infrastructure.web.dto.request.DocumentQueryParamRequest;
import com.wmc.guiaremision.infrastructure.web.dto.response.DocumentsResponse;
import com.wmc.guiaremision.infrastructure.web.dto.response.PaginationListResponse;
import com.wmc.guiaremision.shared.common.Constant;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.shared.common.enums.LinksFileEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {

  @Mapping(target = "documentCode", source = "queryParam", qualifiedByName = "buildDocumentCode")
  @Mapping(target = "startDate", source = "startDate")
  @Mapping(target = "endDate", source = "endDate")
  @Mapping(target = "statusSunat", source = "statusSunat")
  @Mapping(target = "page", source = "page", qualifiedByName = "pagePositive")
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
      DocumentFindAllResponse documentFindAllResponse);

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
    return Optional.ofNullable(queryParam.getDocumentSerie())
        .flatMap(serie -> Optional.ofNullable(queryParam.getDocumentNumber())
            .map(numero -> String.valueOf(Integer.parseInt(numero)))
            .map(numero -> serie.concat(Constant.DASH).concat(numero)))
        .orElse(null);
  }

  @Named("pagePositive")
  default Integer pagePositive(int page) {
    return Optional.of(page)
        .map(currentPage -> currentPage > 0 ? currentPage - 1 : 0)
        .orElse(0);
  }

  @Named("mapDocumentTypeDescription")
  default String mapDocumentTypeDescription(TipoDocumentoEnum documentType) {
    return Optional.ofNullable(documentType)
        .map(TipoDocumentoEnum::getDescripcion)
        .orElse(null);
  }

  @Named("mapLinks")
  default ServiceResponse.Links mapLinks(String requestId) {
    return ServiceResponse.Links.builder()
        .xml(Util.buildUrl(LinksFileEnum.XML.getUrlFile(), requestId))
        .pdf(Util.buildUrl(LinksFileEnum.PDF.getUrlFile(), requestId))
        .cdr(Util.buildUrl(LinksFileEnum.CDR.getUrlFile(), requestId))
        .build();
  }
}

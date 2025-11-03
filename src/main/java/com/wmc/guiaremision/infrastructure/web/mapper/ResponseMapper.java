package com.wmc.guiaremision.infrastructure.web.mapper;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResponseMapper {

  @Mapping(target = "data", source = "object")
  @Mapping(target = "success", constant = "true")
  @Mapping(target = "response.code", expression = "java(org.springframework.http.HttpStatus.CREATED)")
  @Mapping(target = "response.description", constant = "Se ha creado exitosamente.")
  ServiceResponse mapperToServiceResponseCreate(Object object);

  @Mapping(target = "links", source = "links")
  @Mapping(target = "success", constant = "true")
  @Mapping(target = "response.code", expression = "java(org.springframework.http.HttpStatus.OK)")
  @Mapping(target = "response.description", constant = "Se ha creado exitosamente.")
  ServiceResponse mapperToServiceResponseOkWithLink(ServiceResponse.Links links);

  /**
   * Crea un ServiceResponse de error genérico
   *
   * @return ServiceResponse con error interno del servidor
   */
  default ServiceResponse mapperToServiceResponseError() {
    return ServiceResponse.builder()
        .success(false)
        .response(ServiceResponse.Response.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR)
            .description("Error interno del servidor.")
            .build())
        .build();
  }
}

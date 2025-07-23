package com.wmc.guiaremision.web.mapper;

import com.wmc.guiaremision.domain.model.*;
import com.wmc.guiaremision.web.dto.request.CrearGuiaRemisionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GuiaRemisionMapper {
    GuiaRemisionMapper INSTANCE = Mappers.getMapper(GuiaRemisionMapper.class);

    Dispatch toDomain(CrearGuiaRemisionDto dto);

    CrearGuiaRemisionDto toDto(Dispatch entity);

    Contributor toDomain(ContribuyenteDto dto);

    ContribuyenteDto toDto(Contributor entity);

    Address toDomain(DireccionDto dto);

    DireccionDto toDto(Address entity);

    RelatedDocument toDomain(DocumentoRelacionadoDto dto);

    DocumentoRelacionadoDto toDto(RelatedDocument entity);

    DispatchDetail toDomain(DetalleGuiaDto dto);

    DetalleGuiaDto toDto(DispatchDetail entity);
}
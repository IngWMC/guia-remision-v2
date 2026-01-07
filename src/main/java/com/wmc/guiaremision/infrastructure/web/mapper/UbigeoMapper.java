package com.wmc.guiaremision.infrastructure.web.mapper;

import com.wmc.guiaremision.domain.entity.DepartmentEntity;
import com.wmc.guiaremision.domain.entity.DistrictEntity;
import com.wmc.guiaremision.domain.entity.ProvinceEntity;
import com.wmc.guiaremision.infrastructure.web.dto.response.UbigeoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UbigeoMapper {

  @Mapping(target = "codigo", source = "departmentId")
  @Mapping(target = "nombre", source = "name")
  UbigeoResponse toDepartmentsResponse(DepartmentEntity entity);

  @Mapping(target = "codigo", source = "provinceId")
  @Mapping(target = "nombre", source = "name")
  UbigeoResponse toProvincesResponse(ProvinceEntity entity);

  @Mapping(target = "codigo", source = "districtId")
  @Mapping(target = "nombre", source = "name")
  UbigeoResponse toDistrictsResponse(DistrictEntity entity);
}


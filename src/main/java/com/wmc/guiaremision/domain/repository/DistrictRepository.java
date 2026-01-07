package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.DistrictEntity;

import java.util.List;

public interface DistrictRepository extends BaseCrud<DistrictEntity, Integer> {
  List<DistrictEntity> findByProvinceId(Integer provinceId);
}

package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.entity.DistrictEntity;

import java.util.List;

public interface DistrictService {
  boolean existsById(Integer id);
  List<DistrictEntity> findByProvinceId(Integer provinceId);
  DistrictEntity findByDistrictId(Integer districtId);
}

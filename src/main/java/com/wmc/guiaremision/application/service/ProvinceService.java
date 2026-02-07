package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.entity.ProvinceEntity;

import java.util.List;

public interface ProvinceService {
  List<ProvinceEntity> findByDepartmentId(Integer departmentId);
  ProvinceEntity findByProvinceId(Integer provinceId);
}

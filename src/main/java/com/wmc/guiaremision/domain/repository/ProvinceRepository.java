package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.ProvinceEntity;

import java.util.List;

public interface ProvinceRepository {
  List<ProvinceEntity> findByDepartmentId(Integer departmentId);
}

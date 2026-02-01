package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.DepartmentEntity;

import java.util.List;

public interface DepartmentRepository extends BaseCrud<DepartmentEntity, Integer> {
  List<DepartmentEntity> findAll();
}

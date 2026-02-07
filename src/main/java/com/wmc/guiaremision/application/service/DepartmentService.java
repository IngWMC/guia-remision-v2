package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.entity.DepartmentEntity;

import java.util.List;

public interface DepartmentService {
  List<DepartmentEntity> findAll();
  DepartmentEntity findByDepartmentId(Integer departmentId);
}

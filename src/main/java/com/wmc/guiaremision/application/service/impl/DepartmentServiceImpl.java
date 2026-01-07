package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.DepartmentService;
import com.wmc.guiaremision.domain.entity.DepartmentEntity;
import com.wmc.guiaremision.domain.model.enums.StatusEnum;
import com.wmc.guiaremision.domain.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

  private final DepartmentRepository departmentRepository;

  @Override
  public List<DepartmentEntity> findAll() {
    return this.departmentRepository.findAll().stream()
        .filter(d -> d.getStatus().equals(StatusEnum.ACTIVE.getCode()))
        .toList();
  }
}

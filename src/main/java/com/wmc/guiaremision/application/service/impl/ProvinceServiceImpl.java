package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.ProvinceService;
import com.wmc.guiaremision.domain.entity.ProvinceEntity;
import com.wmc.guiaremision.domain.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

  private final ProvinceRepository provinceRepository;

  @Override
  public List<ProvinceEntity> findByDepartmentId(Integer departmentId) {
    return this.provinceRepository.findByDepartmentId(departmentId);
  }
}

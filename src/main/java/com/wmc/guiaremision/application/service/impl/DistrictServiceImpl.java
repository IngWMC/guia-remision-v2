package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.DistrictService;
import com.wmc.guiaremision.domain.entity.DistrictEntity;
import com.wmc.guiaremision.domain.repository.DistrictRepository;
import com.wmc.guiaremision.shared.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

  private final DistrictRepository districtRepository;

  @Override
  public boolean existsById(Integer id) {
    return this.districtRepository.existsById(id);
  }

  @Override
  public List<DistrictEntity> findByProvinceId(Integer provinceId) {
    return this.districtRepository.findByProvinceId(provinceId);
  }

  @Override
  public DistrictEntity findByDistrictId(Integer districtId) {
    return this.districtRepository.findById(districtId)
        .orElseThrow(() -> new BadRequestException("El distrito no se encuentra registrado"));
  }
}

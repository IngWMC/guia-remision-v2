package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.DistrictService;
import com.wmc.guiaremision.domain.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

  private final DistrictRepository districtRepository;

  @Override
  public boolean existsById(Integer id) {
    return this.districtRepository.existsById(id);
  }
}

package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.service.CompanyService;
import com.wmc.guiaremision.application.service.DistrictService;
import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.shared.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final DistrictService districtService;
  private final CompanyRepository companyRepository;

  @Override
  public CompanyEntity save(CompanyEntity company) {
    boolean existsCompany = this.companyRepository
        .existsByIdentityDocumentNumber(company.getIdentityDocumentNumber());
    if (existsCompany) {
      throw new BadRequestException("La empresa ya se encuentra registrada");
    }

    boolean existsDistrict = this.districtService
        .existsById(company.getDistrictId());
    if (existsDistrict) {
      throw new BadRequestException("El distrito no se encuentra registrada");
    }

    company.setUserCreate(Util.getCurrentUsername());
    return this.companyRepository.save(company);
  }
}

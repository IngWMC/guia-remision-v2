package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.CompanyFindAllRequest;
import com.wmc.guiaremision.application.dto.FindAllResponse;
import com.wmc.guiaremision.application.service.CompanyService;
import com.wmc.guiaremision.application.service.DistrictService;
import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import com.wmc.guiaremision.domain.spi.security.EncryptorSecurity;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.shared.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final EncryptorSecurity encryptorSecurity;
  private final DistrictService districtService;
  private final CompanyRepository companyRepository;

  @Override
  public FindAllResponse<CompanyEntity> findAll(CompanyFindAllRequest request) {
    Sort sort = request.getSortDir().equalsIgnoreCase("desc")
        ? Sort.by(request.getSortBy()).descending()
        : Sort.by(request.getSortBy()).ascending();
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

    Integer identityDocumentType = Optional.ofNullable(request.getIdentityDocumentType())
        .map(i -> Integer.valueOf(i.getCodigo()))
        .orElse(null);

    Page<CompanyEntity> result = this.companyRepository.findAll(
        identityDocumentType,
        request.getIdentityDocumentNumber(),
        request.getLegalName(),
        "A",
        pageable
    );

    List<CompanyEntity> companies = result.getContent();

    return new FindAllResponse<>(
        companies,
        result.getNumber(),
        result.getSize(),
        result.getTotalElements(),
        result.getTotalPages(),
        result.hasNext(),
        result.hasPrevious());
  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public CompanyEntity save(CompanyEntity company) {
    boolean existsCompany = this.companyRepository
        .existsByIdentityDocumentNumber(company.getIdentityDocumentNumber());
    if (existsCompany) {
      throw new BadRequestException("La empresa ya se encuentra registrada");
    }

    boolean existsDistrict = this.districtService
        .existsById(company.getDistrictId());
    if (!existsDistrict) {
      throw new BadRequestException("El distrito no se encuentra registrada");
    }

    String encryptSolPassword = this.encryptorSecurity.encrypt(company.getSolPassword());
    company.setSolPassword(encryptSolPassword);
    company.setUserCreate(Util.getCurrentUsername());

    return this.companyRepository.save(company);
  }

  @Override
  public CompanyEntity findByIdentityDocumentNumber(String identityDocumentNumber) {
    return this.companyRepository
        .findByIdentityDocumentNumber(identityDocumentNumber)
        .orElseThrow(() -> new BadRequestException("La empresa no se encuentra registrada"));
  }
}

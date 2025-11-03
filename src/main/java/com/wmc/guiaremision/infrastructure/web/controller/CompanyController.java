package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.CompanyService;
import com.wmc.guiaremision.infrastructure.web.dto.request.SaveCompanyRequest;
import com.wmc.guiaremision.infrastructure.web.mapper.CompanyMapper;
import com.wmc.guiaremision.infrastructure.web.mapper.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

  private final ResponseMapper responseMapper;
  private final CompanyMapper companyMapper;
  private final CompanyService companyService;

  @PostMapping
  public ResponseEntity<ServiceResponse> save(@Valid @RequestBody SaveCompanyRequest request) {
    log.info("Recibida solicitud para crear una nueva empresa: {}", request.getRazonSocial());

    return Optional.of(request)
        .map(this.companyMapper::mapperSaveCompanyRequestToCompanyEntity)
        .map(this.companyService::save)
        .map(this.companyMapper::mapperCompanyEntityToSaveCompanyResponse)
        .map(this.responseMapper::mapperToServiceResponseCreate)
        .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.mapperToServiceResponseError()));
  }
}

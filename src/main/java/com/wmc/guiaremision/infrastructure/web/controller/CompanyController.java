package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.CompanyService;
import com.wmc.guiaremision.infrastructure.web.dto.request.CompanyQueryParamRequest;
import com.wmc.guiaremision.infrastructure.web.dto.request.CompanyRequest;
import com.wmc.guiaremision.infrastructure.web.mapper.CompanyMapper;
import com.wmc.guiaremision.infrastructure.web.mapper.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<ServiceResponse> save(@Valid @RequestBody CompanyRequest request) {
    log.info("Recibida solicitud para crear una nueva empresa: {}", request.getRazonSocial());

    return Optional.of(request)
        .map(this.companyMapper::toCompanyEntity)
        .map(this.companyService::save)
        .map(this.companyMapper::toSaveCompanyResponse)
        .map(this.responseMapper::toServiceResponseCreate)
        .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.toServiceResponseError()));
  }

  @PatchMapping
  public ResponseEntity<ServiceResponse> update(@Valid @RequestBody CompanyRequest request) {
    log.info("Recibida solicitud para actualizar la empresa: {}", request.getRazonSocial());

    return Optional.of(request)
        .map(this.companyMapper::toCompanyEntity)
        .map(this.companyService::update)
        .map(companyEntity -> this.responseMapper.toServiceResponseNoContent())
        .map(resp -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(resp))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.toServiceResponseError()));
  }

  @GetMapping
  public ResponseEntity<ServiceResponse> findAll(CompanyQueryParamRequest queryParam) {
    log.info("Inicio :: CompanyController :: findAll");

    return Optional.of(queryParam)
        .map(this.companyMapper::toCompanyFindAllRequest)
        .map(this.companyService::findAll)
        .map(this.companyMapper::toCompanyFindAllResponse)
        .map(this.responseMapper::toServiceResponseOkWithList)
        .map(ResponseEntity.ok()::body)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.toServiceResponseError()));
  }

  @GetMapping("/{identityDocumentNumber}")
  public ResponseEntity<ServiceResponse> find(@PathVariable String identityDocumentNumber) {
    log.info("Recibida solicitud para obtener empresa con numero de identidad: {}",
        identityDocumentNumber);

    return Optional.ofNullable(identityDocumentNumber)
        .map(this.companyService::findByIdentityDocumentNumber)
        .map(this.companyMapper::toCompanyResponse)
        .map(this.responseMapper::toServiceResponseOk)
        .map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.toServiceResponseError()));
  }
}

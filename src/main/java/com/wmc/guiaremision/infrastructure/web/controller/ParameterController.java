package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.ParameterService;
import com.wmc.guiaremision.infrastructure.web.dto.request.SaveParameterRequest;
import com.wmc.guiaremision.infrastructure.web.mapper.ParameterMapper;
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
@RequestMapping("/api/v1/parameters")
@RequiredArgsConstructor
public class ParameterController {

  private final ResponseMapper responseMapper;
  private final ParameterMapper parameterMapper;
  private final ParameterService parameterService;

  @PostMapping
  public ResponseEntity<ServiceResponse> save(@Valid @RequestBody SaveParameterRequest request) {
    log.info("Recibida solicitud para crear un nuevo parámetro: {}", request.getRucEmpresa());

    return Optional.of(request)
        .map(this.parameterMapper::mapperSaveParameterRequestToParameterEntity)
        .map(entity -> this.parameterService.save(entity, request.getRucEmpresa()))
        .map(entity -> this.parameterMapper.mapperParameterEntityToSaveParameterResponse(entity, request.getRucEmpresa()))
        .map(this.responseMapper::mapperToServiceResponseCreate)
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.mapperToServiceResponseError()));
  }
}

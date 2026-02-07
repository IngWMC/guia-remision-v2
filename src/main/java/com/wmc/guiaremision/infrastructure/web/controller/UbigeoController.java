package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DepartmentService;
import com.wmc.guiaremision.application.service.DistrictService;
import com.wmc.guiaremision.application.service.ProvinceService;
import com.wmc.guiaremision.infrastructure.web.dto.response.UbigeoResponse;
import com.wmc.guiaremision.infrastructure.web.mapper.UbigeoMapper;
import com.wmc.guiaremision.infrastructure.web.mapper.ResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/ubigeo")
@RequiredArgsConstructor
public class UbigeoController {

  private final DepartmentService departmentService;
  private final ProvinceService provinceService;
  private final DistrictService districtService;
  private final UbigeoMapper ubigeoMapper;
  private final ResponseMapper responseMapper;

  @GetMapping("/departments")
  public ResponseEntity<ServiceResponse> findAll() {
    log.info("Inicio :: UbigeoController :: Departments :: findAll");

    List<UbigeoResponse> lista = Optional.ofNullable(this.departmentService.findAll())
        .orElse(Collections.emptyList())
        .stream()
        .map(this.ubigeoMapper::toDepartmentsResponse)
        .collect(Collectors.toList());

    return ResponseEntity.ok(this.responseMapper.toServiceResponseOkWithList(lista));
  }

  @GetMapping("/departments/{departmentId}/provinces")
  public ResponseEntity<ServiceResponse> findByDepartmentId(@PathVariable() Integer departmentId) {
    log.info("Inicio :: UbigeoController :: Provinces :: findAll");

    List<UbigeoResponse> lista = Optional.ofNullable(this.provinceService
            .findByDepartmentId(departmentId))
        .orElse(Collections.emptyList())
        .stream()
        .map(this.ubigeoMapper::toProvincesResponse)
        .collect(Collectors.toList());

    return ResponseEntity.ok(this.responseMapper.toServiceResponseOkWithList(lista));
  }

  @GetMapping("/provinces/{provinceId}/districts")
  public ResponseEntity<ServiceResponse> findByProvinceId(@PathVariable() Integer provinceId) {
    log.info("Inicio :: UbigeoController :: Districts :: findAll");

    List<UbigeoResponse> lista = Optional.ofNullable(this.districtService
            .findByProvinceId(provinceId))
        .orElse(Collections.emptyList())
        .stream()
        .map(this.ubigeoMapper::toDistrictsResponse)
        .collect(Collectors.toList());

    return ResponseEntity.ok(this.responseMapper.toServiceResponseOkWithList(lista));
  }
}

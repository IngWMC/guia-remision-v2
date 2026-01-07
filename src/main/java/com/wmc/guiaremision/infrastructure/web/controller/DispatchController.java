package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.infrastructure.web.dto.request.GenerateGreRequest;
import com.wmc.guiaremision.infrastructure.web.mapper.ResponseMapper;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.infrastructure.web.mapper.GuiaRemisionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controlador REST para la gestión de guías de remisión electrónicas.
 *
 * <p>
 * Proporciona endpoints para la creación y gestión de guías de remisión
 * siguiendo los estándares de la SUNAT.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/dispatches")
@RequiredArgsConstructor
public class DispatchController {

  private final DispatchService dispatchService;
  private final GuiaRemisionMapper guiaRemisionMapper;
  private final ResponseMapper responseMapper;

  /**
   * Genera una guía de remisión electrónica a partir de los datos proporcionados.
   *
   * @param request Los datos de la guía de remisión a generar
   * @return Respuesta con el resultado de la generación
   */
  @PostMapping
  public ResponseEntity<ServiceResponse> generateDispatch(
      @Valid @RequestBody GenerateGreRequest request) {
    log.info("Recibida solicitud para generar guía de remisión: {}", request.getCodigoDocumento());

    return Optional.of(request)
        .map(this.guiaRemisionMapper::mapperGenerateGreRequestToDispatch)
        .map(this.dispatchService::generateDispatch)
        .map(response -> Util.buildFileUrl(response.getRequestId()))
        .map(this.responseMapper::toServiceResponseOkWithLink)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.toServiceResponseError()));
  }
}
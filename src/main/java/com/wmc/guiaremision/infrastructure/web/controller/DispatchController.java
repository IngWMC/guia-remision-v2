package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.infrastructure.common.Util;
import com.wmc.guiaremision.infrastructure.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.infrastructure.web.mapper.GuiaRemisionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
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
@RequestMapping("/api/dispatch/v1/documento")
@RequiredArgsConstructor
public class DispatchController {
    private final DispatchService dispatchService;
    private final GuiaRemisionMapper guiaRemisionMapper;

    /**
     * Genera una guía de remisión electrónica a partir de los datos proporcionados.
     *
     * @param dto Los datos de la guía de remisión a generar
     * @return Respuesta con el resultado de la generación
     */
    @PostMapping
    public ResponseEntity<ServiceResponse> generateDispatch(@Valid @RequestBody CrearGuiaRemisionDto dto) {
        log.info("Recibida solicitud para generar guía de remisión: {}", dto.getCodigoDocumento());

        return Optional.of(dto)
            .map(this.guiaRemisionMapper::mapperCrearGuiaRemisionDtotoDispatch)
            .map(this.dispatchService::generateDispatch)
            .map(serviceResponse -> ServiceResponse.builder()
                .requestId(serviceResponse.getRequestId())
                .links(ServiceResponse.Links.builder()
                    .xml(Util.buildUrl("api/dispatch/v1/file/download/xml", serviceResponse.getRequestId()))
                    .pdf(Util.buildUrl("api/dispatch/v1/file/download/pdf", serviceResponse.getRequestId()))
                    .cdr(Util.buildUrl("api/dispatch/v1/file/download/cdr", serviceResponse.getRequestId()))
                    .build())
                .response(ServiceResponse.Response.builder()
                    .code(HttpStatus.OK)
                    .description(String.format("Guía de remisión %s generada correctamente", dto.getCodigoDocumento()))
                    .build())
                .build())
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new IllegalStateException("Error al mapear DTO a objeto Dispatch"));
    }
}
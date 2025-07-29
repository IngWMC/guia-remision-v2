package com.wmc.guiaremision.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.web.mapper.GuiaRemisionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

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
@RequestMapping("/api/guias-remision/v2/documento")
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
        log.info("Recibida solicitud para generar guía de remisión: {}", dto.getNumeroDocumento());

        try {
            // Convertir DTO a objeto del dominio
            Dispatch dispatch = guiaRemisionMapper.toDispatch(dto);

            // Generar la guía de remisión
            ServiceResponse result = dispatchService.generateDispatch(dispatch);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error al generar guía de remisión: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    ServiceResponse.failure("Error al generar guía de remisión: " + e.getMessage()));
        }
    }
}
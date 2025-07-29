package com.wmc.guiaremision.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.DispatchService;
import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.web.dto.request.CrearGuiaRemisionDto;
import com.wmc.guiaremision.web.mapper.GuiaRemisionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/guias-remision/v2/documento")
@RequiredArgsConstructor
public class DispatchController {
    private final DispatchService dispatchService;
    private final GuiaRemisionMapper guiaRemisionMapper;

    @PostMapping
    public ResponseEntity<?> generateDispatch(@Valid @RequestBody CrearGuiaRemisionDto dto) {
        // Dispatch domain = guiaRemisionMapper.toDomain(dto);
        // ServiceResponse result = dispatchService.generateDispatch(domain);
        return ResponseEntity.ok("result");
    }
}
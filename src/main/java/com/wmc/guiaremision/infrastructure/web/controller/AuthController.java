package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.AuthService;
import com.wmc.guiaremision.infrastructure.web.dto.request.LoginRequest;
import com.wmc.guiaremision.infrastructure.web.mapper.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final ResponseMapper responseMapper;
  private final AuthService authenticateService;

  @PostMapping("/login")
  public ResponseEntity<ServiceResponse> login(@Valid @RequestBody LoginRequest request) {
    log.info("Intento de login para usuario: {}", request.getUsername());

    return Optional.of(request)
        .map(req -> this.authenticateService.authenticate(
            request.getUsername(), request.getPassword()))
        .map(this.responseMapper::toServiceResponseOkWithJwt)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(this.responseMapper.toServiceResponseError()));
  }
}

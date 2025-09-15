package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.AuthService;
import com.wmc.guiaremision.domain.spi.security.TokenProvider;
import com.wmc.guiaremision.infrastructure.web.dto.request.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authenticateService;
  private final TokenProvider tokenProvider;

  @PostMapping("/login")
  public ResponseEntity<ServiceResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    log.info("Intento de login para usuario: {}", loginRequest.getUsername());

    ServiceResponse response = authenticateService.authenticate(
        loginRequest.getUsername(),
        loginRequest.getPassword());

    log.info("Login exitoso para usuario: {}", loginRequest.getUsername());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/validate")
  public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
    String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    boolean isValid = tokenProvider.validateToken(cleanToken);
    return ResponseEntity.ok(isValid);
  }
}

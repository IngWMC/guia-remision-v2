package com.wmc.guiaremision.shared.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.infrastructure.web.dto.response.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointException implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException)
      throws IOException, ServletException {
    log.error("Error: {} - {}", authException.getMessage(),
        authException.getStackTrace());

    ExceptionResponse errorResponse = ExceptionResponse.builder()
        .fecha(Util.getCurrentDateTime())
        .mensaje("Token JWT requerido")
        .detalle("Debe proporcionar un token JWT válido en el header Authorization")
        .build();

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}

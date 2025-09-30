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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerException implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request,
                     HttpServletResponse response,
                     AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    log.error("Error: {} - {}", accessDeniedException.getMessage(),
        accessDeniedException.getStackTrace());

    ExceptionResponse errorResponse = ExceptionResponse.builder()
        .fecha(Util.getCurrentDateTime())
        .mensaje("Acceso denegado")
        .detalle("No tiene permisos para acceder a este recurso")
        .build();

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}

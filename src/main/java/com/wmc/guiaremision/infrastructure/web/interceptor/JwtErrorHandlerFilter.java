package com.wmc.guiaremision.infrastructure.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wmc.guiaremision.infrastructure.web.dto.response.ExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtErrorHandlerFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  private static final String ZONE_ID = "America/Lima";
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String HOUR_FORMAT = "HH:mm:ss";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (AuthenticationException ex) {
      handleAuthenticationError(request, response, ex);
    }
  }

  private void handleAuthenticationError(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException ex) throws IOException {

    log.warn("Error de autenticación en {}: {}", request.getRequestURI(), ex.getMessage());

    ExceptionResponse errorResponse = ExceptionResponse.builder()
        .fecha(getCurrentDateTime())
        .mensaje("Token JWT requerido")
        .detalle("Debe proporcionar un token JWT válido en el header Authorization")
        .build();

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }

  private String getCurrentDateTime() {
    ZonedDateTime nowInPeru = ZonedDateTime.now(ZoneId.of(ZONE_ID));
    return nowInPeru.format(DateTimeFormatter.ofPattern(DATE_FORMAT
        .concat(" ")
        .concat(HOUR_FORMAT)));
  }
}

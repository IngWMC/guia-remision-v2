package com.wmc.guiaremision.infrastructure.web.interceptor;

import com.wmc.guiaremision.domain.spi.security.TokenProvider;
import com.wmc.guiaremision.infrastructure.web.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = this.extractTokenFromRequest(request);

    if (this.tokenProvider.validateToken(token)) {
      try {
        String userName = this.tokenProvider.getUserNameFromToken(token);
        if (userName != null) {
          List<String> roles = List.of(this.tokenProvider.getRolesFromToken(token).split(","));
          List<SimpleGrantedAuthority> authorities = roles.stream()
              .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol))
              .collect(Collectors.toList());

          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userName, null, authorities);

          SecurityContextHolder.getContext().setAuthentication(authentication);
          log.debug("Usuario autenticado: {} con roles: {}", userName, roles);
        }
      } catch (Exception e) {
        log.error("Error procesando token JWT: {}", e.getMessage());
        SecurityContextHolder.clearContext();
      }
    } else {
      log.warn("Token JWT inválido en la petición: {}", request.getRequestURI());
      throw new UnauthorizedException("Token JWT inválido o expirado");
    }

    filterChain.doFilter(request, response);
  }

  private String extractTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}

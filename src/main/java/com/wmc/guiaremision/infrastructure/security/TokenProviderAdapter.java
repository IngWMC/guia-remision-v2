package com.wmc.guiaremision.infrastructure.security;

import com.wmc.guiaremision.domain.entity.RolEntity;
import com.wmc.guiaremision.domain.entity.UserEntity;
import com.wmc.guiaremision.domain.repository.UserRepository;
import com.wmc.guiaremision.domain.spi.security.TokenProvider;
import com.wmc.guiaremision.domain.spi.security.dto.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProviderAdapter implements TokenProvider {

  private final UserRepository userRepository;

  @Value("${jwt.secret:mySecretKeyForJWTTokenGeneration123456789}")
  private String jwtSecret;

  @Value("${jwt.expiration:86400000}")
  private Long jwtExpiration;

  @Override
  public JwtToken generateToken(UserEntity user, List<RolEntity> listRoles) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpiration);

    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    String roles = listRoles.stream()
        .map(RolEntity::getNombre)
        .collect(Collectors.joining(","));

    String token = Jwts.builder()
        .setSubject(user.getUserName())
        .claim("userId", user.getUserId())
        .claim("companyId", user.getCompanyId())
        .claim("roles", roles)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    return JwtToken.builder()
        .token(token)
        .type("Bearer")
        .expiresIn(jwtExpiration)
        .build();
  }

  @Override
  public boolean validateToken(String token) {
    try {
      Claims claims = this.getClaimsFromToken(token);
      return !claims.getExpiration().before(new Date());
    } catch (Exception e) {
      log.error("Error validando token: {}", e.getMessage());
      return false;
    }
  }

  @Override
  public String getUserNameFromToken(String token) {
    Claims claims = this.getClaimsFromToken(token);
    return claims.getSubject();
  }

  @Override
  public String getRolesFromToken(String token) {
    Claims claims = this.getClaimsFromToken(token);
    return claims.get("roles", String.class);
  }

  private Claims getClaimsFromToken(String token) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}

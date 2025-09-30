package com.wmc.guiaremision.infrastructure.security;

import com.wmc.guiaremision.domain.entity.RolEntity;
import com.wmc.guiaremision.domain.entity.UserEntity;
import com.wmc.guiaremision.domain.spi.security.TokenProvider;
import com.wmc.guiaremision.domain.spi.security.dto.JwtToken;
import com.wmc.guiaremision.infrastructure.config.property.JwtProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProviderAdapter implements TokenProvider {

  private final JwtProperty jwtProperty;

  @Override
  public JwtToken generateToken(UserEntity user, List<RolEntity> listRoles) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtProperty.getExpiration());

    String roles = listRoles.stream()
        .map(RolEntity::getName)
        .collect(Collectors.joining(","));

    String token = Jwts.builder()
        .setSubject(user.getUserName())
        .claim("roles", roles)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(this.getKey(), SignatureAlgorithm.HS256)
        .compact();

    return JwtToken.builder()
        .token(token)
        .type("Bearer")
        .expiresIn(jwtProperty.getExpiration())
        .build();
  }

  @Override
  public boolean validateToken(String token) {
    try {
      return !this.getExpiration(token).before(new Date());
    } catch (Exception e) {
      log.error("Error validando token: {}", e.getMessage());
      return false;
    }
  }

  @Override
  public String getUserNameFromToken(String token) {
    return this.getClaim(token, Claims::getSubject);
  }

  @Override
  public String getRolesFromToken(String token) {
    Claims claims = this.getAllClaims(token);
    return claims.get("roles", String.class);
  }

  private Claims getAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(this.getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Date getExpiration(String token) {
    return this.getClaim(token, Claims::getExpiration);
  }

  private SecretKey getKey() {
    byte[] keyBytes = jwtProperty.getSecret().getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

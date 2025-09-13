package com.wmc.guiaremision.domain.spi.security;

import com.wmc.guiaremision.domain.entity.RolEntity;
import com.wmc.guiaremision.domain.entity.UserEntity;
import com.wmc.guiaremision.domain.spi.security.dto.JwtToken;

import java.util.List;

public interface TokenProvider {
  JwtToken generateToken(UserEntity user, List<RolEntity> listRoles);
  boolean validateToken(String token);
  String getUserNameFromToken(String token);
  String getRolesFromToken(String token);
}

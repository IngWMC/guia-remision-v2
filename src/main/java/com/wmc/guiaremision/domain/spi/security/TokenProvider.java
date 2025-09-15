package com.wmc.guiaremision.domain.spi.security;

import com.wmc.guiaremision.domain.entity.RolEntity;
import com.wmc.guiaremision.domain.entity.UserEntity;
import com.wmc.guiaremision.domain.spi.security.dto.JwtToken;

import java.util.List;

/**
 * Proveedor de operaciones para la gestión de tokens JWT en el dominio de seguridad.
 * Permite generar, validar y extraer información relevante de los tokens JWT.
 */
public interface TokenProvider {
  /**
   * Genera un token JWT a partir de la información del usuario y sus roles.
   *
   * @param user entidad de usuario autenticado
   * @param listRoles lista de roles asociados al usuario
   * @return objeto JwtToken con el token generado
   */
  JwtToken generateToken(UserEntity user, List<RolEntity> listRoles);

  /**
   * Valida la integridad y vigencia de un token JWT.
   *
   * @param token token JWT a validar
   * @return true si el token es válido, false en caso contrario
   */
  boolean validateToken(String token);

  /**
   * Obtiene el nombre de usuario contenido en el token JWT.
   *
   * @param token token JWT
   * @return nombre de usuario extraído del token
   */
  String getUserNameFromToken(String token);

  /**
   * Obtiene los roles asociados al usuario desde el token JWT.
   *
   * @param token token JWT
   * @return roles del usuario en formato String
   */
  String getRolesFromToken(String token);
}

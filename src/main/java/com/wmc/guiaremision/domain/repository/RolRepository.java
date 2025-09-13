package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.RolEntity;
import java.util.List;

public interface RolRepository {
  /**
   * Obtiene la lista de roles asociados a un usuario por su código.
   *
   * @param userId el código único del usuario
   * @return lista de roles
   */
    List<RolEntity> findByUserId(Long userId);
}

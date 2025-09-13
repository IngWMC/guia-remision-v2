package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {
  Optional<UserEntity> findByUserName(String username);
}

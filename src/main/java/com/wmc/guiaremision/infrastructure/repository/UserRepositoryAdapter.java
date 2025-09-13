package com.wmc.guiaremision.infrastructure.repository;

import com.wmc.guiaremision.domain.entity.UserEntity;
import com.wmc.guiaremision.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryAdapter
    extends JpaRepository<UserEntity, Long>, UserRepository {

  @Override
  Optional<UserEntity> findByUserName(String username);
}

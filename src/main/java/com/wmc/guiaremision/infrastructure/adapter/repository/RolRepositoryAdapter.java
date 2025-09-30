package com.wmc.guiaremision.infrastructure.adapter.repository;

import com.wmc.guiaremision.domain.entity.RolEntity;
import com.wmc.guiaremision.domain.repository.RolRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepositoryAdapter
    extends JpaRepository<RolEntity, Long>, RolRepository {

  @Override
  @Query("SELECT r FROM RolEntity r JOIN r.users u WHERE u.userId = :userId")
  List<RolEntity> findByUserId(@Param("userId") Long userId);
}

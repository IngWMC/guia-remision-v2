package com.wmc.guiaremision.infrastructure.adapter.repository;

import com.wmc.guiaremision.domain.entity.ProvinceEntity;
import com.wmc.guiaremision.domain.repository.ProvinceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepositoryAdapter
    extends JpaRepository<ProvinceEntity, Integer>, ProvinceRepository {

  @Query("SELECT p FROM ProvinceEntity p " +
      "WHERE p.departmentId = :departmentId AND p.status = 'A' " +
      "ORDER BY p.name ASC")
  List<ProvinceEntity> findByDepartmentId(@Param("departmentId") Integer departmentId);

}

package com.wmc.guiaremision.infrastructure.adapter.repository;

import com.wmc.guiaremision.domain.entity.DistrictEntity;
import com.wmc.guiaremision.domain.repository.DistrictRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepositoryAdapter
    extends JpaRepository<DistrictEntity, Integer>, DistrictRepository {

  @Query("SELECT d FROM DistrictEntity d " +
      "WHERE d.provinceId = :provinceId AND d.status = 'A' " +
      "ORDER BY d.name ASC")
  List<DistrictEntity> findByProvinceId(@Param("provinceId") Integer provinceId);
}

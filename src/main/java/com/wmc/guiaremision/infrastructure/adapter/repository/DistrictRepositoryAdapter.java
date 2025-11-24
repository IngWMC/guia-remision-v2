package com.wmc.guiaremision.infrastructure.adapter.repository;

import com.wmc.guiaremision.domain.entity.DistrictEntity;
import com.wmc.guiaremision.domain.repository.DistrictRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepositoryAdapter
    extends JpaRepository<DistrictEntity, Integer>, DistrictRepository {
}

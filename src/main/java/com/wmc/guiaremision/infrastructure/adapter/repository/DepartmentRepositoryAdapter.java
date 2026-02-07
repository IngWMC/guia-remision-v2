package com.wmc.guiaremision.infrastructure.adapter.repository;

import com.wmc.guiaremision.domain.entity.DepartmentEntity;
import com.wmc.guiaremision.domain.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositoryAdapter
    extends JpaRepository<DepartmentEntity, Integer>, DepartmentRepository {
}

package com.wmc.guiaremision.infrastructure.repository;

import com.wmc.guiaremision.domain.entity.CompanyEntity;
import com.wmc.guiaremision.domain.repository.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador (implementación) del repositorio de Company en infraestructura
 */
@Repository
public interface CompanyRepositoryImpl extends JpaRepository<CompanyEntity, Integer>, CompanyRepository {

  @Override
  default CompanyEntity save(CompanyEntity companyEntity) {
    return saveAndFlush(companyEntity);
  }

  @Override
  default Optional<CompanyEntity> findById(Integer id) {
    return findById(id);
  }

  @Override
  default List<CompanyEntity> findAll() {
    return findAll();
  }

  @Override
  default void delete(CompanyEntity companyEntity) {
    delete(companyEntity);
  }

  @Override
  Optional<CompanyEntity> findByRuc(String ruc);

  @Override
  List<CompanyEntity> findByNameContainingIgnoreCase(String name);

  @Override
  List<CompanyEntity> findByBusinessNameContainingIgnoreCase(String businessName);

  @Override
  List<CompanyEntity> findByDistrictId(Integer districtId);

  @Override
  List<CompanyEntity> findByParentCompanyIdIsNull();

  @Override
  List<CompanyEntity> findByParentCompanyId(Integer parentCompanyId);

  @Override
  boolean existsByRuc(String ruc);

  @Override
  List<CompanyEntity> findByOnlineMode(Boolean onlineMode);

  @Override
  Optional<CompanyEntity> findBySunatUser(String sunatUser);
}
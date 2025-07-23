package com.wmc.guiaremision.infrastructure.repository;

import com.wmc.guiaremision.domain.model.Company;
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
public interface CompanyRepositoryImpl extends JpaRepository<Company, Integer>, CompanyRepository {

  @Override
  default Company save(Company company) {
    return saveAndFlush(company);
  }

  @Override
  default Optional<Company> findById(Integer id) {
    return findById(id);
  }

  @Override
  default List<Company> findAll() {
    return findAll();
  }

  @Override
  default void delete(Company company) {
    delete(company);
  }

  @Override
  Optional<Company> findByRuc(String ruc);

  @Override
  List<Company> findByNameContainingIgnoreCase(String name);

  @Override
  List<Company> findByBusinessNameContainingIgnoreCase(String businessName);

  @Override
  List<Company> findByDistrictId(Integer districtId);

  @Override
  List<Company> findByParentCompanyIdIsNull();

  @Override
  List<Company> findByParentCompanyId(Integer parentCompanyId);

  @Override
  boolean existsByRuc(String ruc);

  @Override
  List<Company> findByOnlineMode(Boolean onlineMode);

  @Override
  Optional<Company> findBySunatUser(String sunatUser);

  @Override
  @Query("SELECT c FROM Company c LEFT JOIN FETCH c.district d LEFT JOIN FETCH c.identityDocument id WHERE c.companyId = :companyId")
  Optional<Company> findByIdWithDistrictAndIdentityDocument(@Param("companyId") Integer companyId);

  @Override
  @Query("SELECT c FROM Company c WHERE c.onlineMode = true")
  List<Company> findActiveCompanies();

  @Override
  @Query("SELECT c FROM Company c LEFT JOIN FETCH c.district d LEFT JOIN FETCH c.identityDocument id WHERE c.ruc = :ruc")
  Optional<Company> findByRucWithDistrictAndIdentityDocument(@Param("ruc") String ruc);
}
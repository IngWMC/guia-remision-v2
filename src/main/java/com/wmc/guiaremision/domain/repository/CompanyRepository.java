package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.CompanyEntity;

import java.util.List;
import java.util.Optional;

/**
 * Puerto (interface) para el repositorio de Company en el dominio
 */
public interface CompanyRepository {

  /**
   * Guardar una empresa
   */
  CompanyEntity save(CompanyEntity companyEntity);

  /**
   * Buscar empresa por ID
   */
  Optional<CompanyEntity> findById(Integer id);

  /**
   * Buscar todas las empresas
   */
  List<CompanyEntity> findAll();

  /**
   * Eliminar una empresa
   */
  void delete(CompanyEntity companyEntity);

  /**
   * Buscar empresa por RUC
   */
  Optional<CompanyEntity> findByRuc(String ruc);

  /**
   * Buscar empresa por nombre comercial
   */
  List<CompanyEntity> findByNameContainingIgnoreCase(String name);

  /**
   * Buscar empresa por razón social
   */
  List<CompanyEntity> findByBusinessNameContainingIgnoreCase(String businessName);

  /**
   * Buscar empresas por distrito
   */
  List<CompanyEntity> findByDistrictId(Integer districtId);

  /**
   * Buscar empresas padre (sin empresa padre)
   */
  List<CompanyEntity> findByParentCompanyIdIsNull();

  /**
   * Buscar empresas hijas de una empresa padre
   */
  List<CompanyEntity> findByParentCompanyId(Integer parentCompanyId);

  /**
   * Verificar si existe una empresa con el RUC especificado
   */
  boolean existsByRuc(String ruc);

  /**
   * Buscar empresas por modo online
   */
  List<CompanyEntity> findByOnlineMode(Boolean onlineMode);

  /**
   * Buscar empresa por usuario SUNAT
   */
  Optional<CompanyEntity> findBySunatUser(String sunatUser);

  /**
   * Buscar empresas con información completa
   */
  Optional<CompanyEntity> findByIdWithDistrictAndIdentityDocument(Integer companyId);

  /**
   * Buscar empresas activas
   */
  List<CompanyEntity> findActiveCompanies();

  /**
   * Buscar empresa por RUC con información completa
   */
  Optional<CompanyEntity> findByRucWithDistrictAndIdentityDocument(String ruc);
}
package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.model.Company;

import java.util.List;
import java.util.Optional;

/**
 * Puerto (interface) para el repositorio de Company en el dominio
 */
public interface CompanyRepository {

  /**
   * Guardar una empresa
   */
  Company save(Company company);

  /**
   * Buscar empresa por ID
   */
  Optional<Company> findById(Integer id);

  /**
   * Buscar todas las empresas
   */
  List<Company> findAll();

  /**
   * Eliminar una empresa
   */
  void delete(Company company);

  /**
   * Buscar empresa por RUC
   */
  Optional<Company> findByRuc(String ruc);

  /**
   * Buscar empresa por nombre comercial
   */
  List<Company> findByNameContainingIgnoreCase(String name);

  /**
   * Buscar empresa por razón social
   */
  List<Company> findByBusinessNameContainingIgnoreCase(String businessName);

  /**
   * Buscar empresas por distrito
   */
  List<Company> findByDistrictId(Integer districtId);

  /**
   * Buscar empresas padre (sin empresa padre)
   */
  List<Company> findByParentCompanyIdIsNull();

  /**
   * Buscar empresas hijas de una empresa padre
   */
  List<Company> findByParentCompanyId(Integer parentCompanyId);

  /**
   * Verificar si existe una empresa con el RUC especificado
   */
  boolean existsByRuc(String ruc);

  /**
   * Buscar empresas por modo online
   */
  List<Company> findByOnlineMode(Boolean onlineMode);

  /**
   * Buscar empresa por usuario SUNAT
   */
  Optional<Company> findBySunatUser(String sunatUser);

  /**
   * Buscar empresas con información completa
   */
  Optional<Company> findByIdWithDistrictAndIdentityDocument(Integer companyId);

  /**
   * Buscar empresas activas
   */
  List<Company> findActiveCompanies();

  /**
   * Buscar empresa por RUC con información completa
   */
  Optional<Company> findByRucWithDistrictAndIdentityDocument(String ruc);
}
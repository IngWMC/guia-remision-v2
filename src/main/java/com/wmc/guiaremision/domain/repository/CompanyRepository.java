package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.CompanyEntity;

import java.util.Optional;

/**
 * Puerto (interface) para el repositorio de empresas en el dominio.
 * <p>
 * Define las operaciones de acceso a datos específicas para la entidad
 * {@link CompanyEntity}. Esta interface actúa como un puerto en la arquitectura
 * hexagonal, que será implementado por un adaptador en la capa de
 * infraestructura.
 * </p>
 * <p>
 * Extiende {@link BaseCrud} para heredar las operaciones CRUD básicas
 * (create, read, update, delete) y define métodos adicionales específicos
 * del dominio de empresas.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 * @see CompanyEntity
 * @see BaseCrud
 */
public interface CompanyRepository extends BaseCrud<CompanyEntity, Integer> {

  /**
   * Busca una empresa por su número de documento de identidad.
   * <p>
   * Este método permite localizar una empresa utilizando su identificador
   * tributario (RUC, DNI, CE, etc.), que es único por empresa en el sistema.
   * </p>
   *
   * @param identityDocumentNumber el número de documento de identidad de la
   *                               empresa
   *                               (por ejemplo, RUC de 11 dígitos:
   *                               {@code "20123456789"})
   * @return un {@link Optional} que contiene la entidad {@link CompanyEntity}
   *         si se encuentra, o {@link Optional#empty()} si no existe una empresa
   *         con ese número de documento
   * @throws IllegalArgumentException si el número de documento es {@code null} o
   *                                  está vacío
   */
  Optional<CompanyEntity> findByIdentityDocumentNumber(String identityDocumentNumber);

  /**
   * Verifica si existe una empresa con el número de documento de identidad
   * especificado.
   * <p>
   * Este método es útil para validar la unicidad del número de documento
   * antes de crear o actualizar una empresa en el sistema.
   * </p>
   *
   * @param identityDocumentNumber el número de documento de identidad a
   *                               verificar
   *                               (por ejemplo, RUC de 11 dígitos:
   *                               {@code "20123456789"})
   * @return {@code true} si existe una empresa con ese número de documento,
   *         {@code false} en caso contrario
   * @throws IllegalArgumentException si el número de documento es {@code null} o
   *                                  está vacío
   */
  boolean existsByIdentityDocumentNumber(String identityDocumentNumber);
}
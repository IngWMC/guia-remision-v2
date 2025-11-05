package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.entity.ParameterEntity;

import java.util.Optional;

/**
 * Puerto (interface) para el repositorio de parámetros de configuración en el
 * dominio.
 * <p>
 * Define las operaciones de acceso a datos específicas para la entidad
 * {@link ParameterEntity}. Esta interface actúa como un puerto en la
 * arquitectura hexagonal, que será implementado por un adaptador en la capa de
 * infraestructura.
 * </p>
 * <p>
 * Extiende {@link BaseCrud} para heredar las operaciones CRUD básicas
 * (create, read, update, delete) y define métodos adicionales específicos
 * del dominio de parámetros de configuración.
 * </p>
 *
 * @author WMC
 * @version 1.0
 * @since 1.0
 * @see ParameterEntity
 * @see BaseCrud
 */
public interface ParameterRepository extends BaseCrud<ParameterEntity, Integer> {

  /**
   * Busca los parámetros de configuración asociados a una empresa específica.
   * <p>
   * Cada empresa tiene un único conjunto de parámetros de configuración que
   * define las rutas de almacenamiento de archivos y datos del certificado
   * digital necesarios para la emisión de documentos electrónicos.
   * </p>
   *
   * @param companyId el identificador único de la empresa
   * @return un {@link Optional} que contiene la entidad
   *         {@link ParameterEntity}
   *         si se encuentra, o {@link Optional#empty()} si no existen
   *         parámetros configurados para esa empresa
   * @throws IllegalArgumentException si el ID de la empresa es {@code null}
   */
  Optional<ParameterEntity> findByCompanyId(Integer companyId);

  /**
   * Verifica si existen parámetros de configuración para una empresa específica.
   * <p>
   * Este método es útil para determinar si una empresa ya tiene
   * configurados sus parámetros antes de intentar crearlos o actualizarlos.
   * </p>
   *
   * @param companyId el identificador único de la empresa
   * @return {@code true} si existen parámetros para la empresa, {@code false}
   *         en caso contrario
   * @throws IllegalArgumentException si el ID de la empresa es {@code null}
   */
  boolean existsByCompanyId(Integer companyId);
}

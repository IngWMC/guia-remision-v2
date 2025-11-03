package com.wmc.guiaremision.domain.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface genérica que define operaciones CRUD básicas para repositorios.
 *
 * @param <T>  Tipo de la entidad que gestiona el repositorio
 * @param <ID> Tipo del identificador único de la entidad
 * @author WMC
 * @version 1.0
 * @since 1.0
 */
public interface BaseCrud<T, ID> {

  /**
   * Guarda o actualiza una entidad en el repositorio.
   * <p>
   * Si la entidad no existe (ID es null o no se encuentra), se crea una nueva.
   * Si la entidad ya existe, se actualizan sus datos.
   * </p>
   *
   * @param entity la entidad a guardar o actualizar, no puede ser {@code null}
   * @return la entidad guardada con los datos actualizados (incluido el ID generado)
   * @throws IllegalArgumentException si la entidad es {@code null}
   */
  T save(T entity);

  /**
   * Busca una entidad por su identificador único.
   *
   * @param id el identificador de la entidad, no puede ser {@code null}
   * @return un {@link Optional} que contiene la entidad si se encuentra,
   *         o {@link Optional#empty()} si no existe
   * @throws IllegalArgumentException si el ID es {@code null}
   */
  Optional<T> findById(ID id);

  /**
   * Verifica si existe una entidad con el identificador especificado.
   *
   * @param id el identificador de la entidad, no puede ser {@code null}
   * @return {@code true} si existe una entidad con ese ID, {@code false} en caso contrario
   * @throws IllegalArgumentException si el ID es {@code null}
   */
  boolean existsById(ID id);

  /**
   * Obtiene todas las entidades del repositorio.
   *
   * @return lista de todas las entidades, nunca {@code null} pero puede estar vacía
   */
  List<T> findAll();

  /**
   * Elimina una entidad por su identificador.
   * <p>
   * Si el identificador no existe, la operación no tiene efecto
   * (no lanza excepción).
   * </p>
   *
   * @param id el identificador de la entidad a eliminar, no puede ser {@code null}
   * @throws IllegalArgumentException si el ID es {@code null}
   */
  void deleteById(ID id);
}

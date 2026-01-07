package com.wmc.guiaremision.domain.model.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum para los tipos de documento de identidad según catálogo SUNAT.
 * 
 * <p>
 * Este enum define los códigos y descripciones de los tipos de documento
 * de identidad válidos para documentos electrónicos SUNAT.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum TipoDocumentoIdentidadEnum {

  /**
   * Documento Nacional de Identidad (DNI)
   */
  DNI("1", "Documento Nacional de Identidad", "DNI"),

  /**
   * Carnet de extranjería
   */
  CARNET_EXTRANJERIA("4", "Carnet de extranjería", "CE"),

  /**
   * Registro Único de Contribuyentes (RUC)
   */
  RUC("6", "Registro Único de Contribuyentes", "RUC"),

  /**
   * Pasaporte
   */
  PASAPORTE("7", "Pasaporte", "PAS");

  /**
   * Código del tipo de documento según catálogo SUNAT.
   */
  private final String codigo;

  /**
   * Descripción completa del tipo de documento.
   */
  private final String descripcion;

  /**
   * Descripción corta del tipo de documento.
   */
  private final String descripcionCorta;

  /**
   * Obtiene el enum a partir del código usando programación funcional.
   * 
   * @param codigo Código del tipo de documento
   * @return Enum correspondiente al código
   * @throws IllegalArgumentException Si el código no es válido
   * @since 1.0
   */
  public static TipoDocumentoIdentidadEnum fromCodigo(String codigo) {
    return java.util.Optional.ofNullable(codigo)
        .flatMap(c -> Arrays.stream(values())
            .filter(tipo -> tipo.getCodigo().equals(c))
            .findFirst())
        .orElseThrow(() -> new IllegalArgumentException("Código de tipo de documento de identidad no válido: " + codigo));
  }

  /**
   * Verifica si un código es válido usando programación funcional.
   * 
   * @param codigo Código a validar
   * @return true si el código es válido, false en caso contrario
   * @since 1.0
   */
  public static boolean isValid(String codigo) {
    return Optional.ofNullable(codigo)
        .flatMap(c -> Arrays.stream(values())
            .filter(tipo -> tipo.getCodigo().equals(c))
            .findFirst())
        .isPresent();
  }

  /**
   * Obtiene la descripción corta del tipo de documento a partir del código.
   *
   * @param codigo Código del tipo de documento
   * @return descripcionCorta correspondiente al código
   * @throws IllegalArgumentException Si el código no es válido
   * @since 1.0
   */
  public static String getDescripcionCortaByCodigo(String codigo) {
    return Optional.ofNullable(codigo)
        .flatMap(c -> Arrays.stream(values())
            .filter(tipo -> tipo.getCodigo().equals(c))
            .findFirst())
        .map(TipoDocumentoIdentidadEnum::getDescripcionCorta)
        .orElseThrow(() -> new IllegalArgumentException("Código de tipo de documento de identidad no válido: " + codigo));
  }

  /**
   * Obtiene la descripción del tipo de documento.
   * 
   * @return Descripción del tipo de documento
   * @since 1.0
   */
  @Override
  public String toString() {
    return codigo + " - " + descripcion;
  }
}

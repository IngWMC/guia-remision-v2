package com.wmc.guiaremision.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Representa una respuesta estándar para los servicios de la aplicación.
 * <p>
 * Contiene el estado de la operación, datos, paginación, errores y enlaces relevantes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
  /** Indica si la operación fue exitosa. Por defecto es {@code true}. */
  @Builder.Default
  private boolean success = true;
  /** Identificador único de la solicitud. */
  private String requestId;
  /** Información de paginación para resultados listados. */
  private Paging paging;
  /** Datos principales de la respuesta. */
  private Object data;
  /** Mensaje de error en caso de fallo. */
  private String error;
  /** Enlaces a recursos relacionados, como descargas. */
  private Links links;
  /** Metadatos de la respuesta, como el código de estado y el hash. */
  private Response response;

  /**
   * Contiene los datos de paginación de una respuesta.
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Paging {
    /** URL de la primera página de resultados. */
    private String first;
    /** URL de la página anterior de resultados. */
    private String previous;
    /** URL de la página siguiente de resultados. */
    private String next;
    /** URL de la última página de resultados. */
    private String last;

    /** Número total de elementos en todas las páginas. */
    private int totalElements;
    /** Página actual. */
    private int currentPage;
    /** Tamaño de la página. */
    private int pageSize;
    /** Número total de páginas. */
    private int totalPages;
    /** Número de elementos en la página actual. */
    private int numberOfElements;
  }

  /**
   * Contiene los enlaces de descarga para los archivos generados.
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Links {
    /** URL para descargar el archivo XML. */
    private String xml;
    /** URL para descargar el archivo PDF. */
    private String pdf;
    /** URL para descargar el archivo CDR (Constancia de Recepción). */
    private String cdr;
  }

  /**
   * Contiene metadatos sobre la respuesta de una operación.
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Response {
    /** Código de estado HTTP de la respuesta. */
    private HttpStatus code;
    /** Descripción del resultado de la operación. */
    private String description;
    /** Mensaje de error detallado, si lo hubiera. */
    private String mensajeError;
    /** Hash o código de firma del documento generado. */
    private String hash;
  }
}
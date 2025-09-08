package com.wmc.guiaremision.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Modelo de dominio que representa el resultado de una operación de servicio
 * Incluye links de descarga y metadatos de respuesta
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
  private String requestId;
  private Paging paging;
  private Object data;
  private String error;
  @Builder.Default
  private boolean success = true;
  private Links links;
  @Builder.Default
  private Response response = new Response();

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Paging {
    private String first;
    private String previous;
    private String next;
    private String last;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Links {
    private String xml;
    private String pdf;
    private String cdr;
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Response {
    private HttpStatus code;
    private String description;
    private String mensajeError;
    private String hash;
  }
}
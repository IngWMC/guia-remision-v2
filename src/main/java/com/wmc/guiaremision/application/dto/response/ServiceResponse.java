package com.wmc.guiaremision.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponse {

  private String responseId;
  private Paging paging;
  private Object data;
  private String error;
  private boolean success;
  private Links links;
  private Response response;

  public ServiceResponse() {
    this.success = true;
    this.response = new Response();
  }

  public static ServiceResponse success() {
    ServiceResponse result = new ServiceResponse();
    result.setSuccess(true);
    return result;
  }

  public static ServiceResponse success(Object data) {
    ServiceResponse result = new ServiceResponse();
    result.setSuccess(true);
    result.setData(data);
    return result;
  }

  public static ServiceResponse failure(String error) {
    ServiceResponse result = new ServiceResponse();
    result.setSuccess(false);
    result.setError(error);
    return result;
  }

  public static ServiceResponse failure() {
    return failure("Error en el flujo de generación");
  }

  @Data
  public static class Paging {
    private String first;
    private String previous;
    private String next;
    private String last;
  }

  @Data
  public static class Links {
    private String xml;
    private String pdf;
    private String cdr;
  }

  @Data
  public static class Response {
    private int code;
    private String description;
    private String mensajeError;
    private String hash;

    public Response() {
      this.code = 200;
      this.description = "OK";
    }
  }
}
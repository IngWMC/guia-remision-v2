package com.wmc.guiaremision.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

  private boolean success;
  private String message;
  private T data;
  private LocalDateTime timestamp;
  private String requestId;

  public static <T> ApiResponse<T> success(T data) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(true);
    response.setMessage("Operación exitosa");
    response.setData(data);
    response.setTimestamp(LocalDateTime.now());
    return response;
  }

  public static <T> ApiResponse<T> success(T data, String message) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(true);
    response.setMessage(message);
    response.setData(data);
    response.setTimestamp(LocalDateTime.now());
    return response;
  }

  public static <T> ApiResponse<T> error(String message) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setMessage(message);
    response.setTimestamp(LocalDateTime.now());
    return response;
  }

  public static <T> ApiResponse<T> error(String message, T data) {
    ApiResponse<T> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setMessage(message);
    response.setData(data);
    response.setTimestamp(LocalDateTime.now());
    return response;
  }
}
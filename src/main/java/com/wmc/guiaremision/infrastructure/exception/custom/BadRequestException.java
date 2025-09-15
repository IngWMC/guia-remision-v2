package com.wmc.guiaremision.infrastructure.exception.custom;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}

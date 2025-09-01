package com.wmc.guiaremision.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CdrDataResponse {
  private String code;
  private String message;
  private String qrUrl;
  private String ticketSunat;
  private String note;
}

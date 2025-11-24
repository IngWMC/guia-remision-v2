package com.wmc.guiaremision.infrastructure.web.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentQueryParamRequest {

  private String documentSerie;
  private String documentNumber;
  private LocalDate startDate;
  private LocalDate endDate;
  private String statusSunat;
  private int page;
  private int size;
  private String sortBy;
  private String sortDir;

}

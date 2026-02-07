package com.wmc.guiaremision.infrastructure.web.dto.request;

import lombok.Data;

@Data
public class CompanyQueryParamRequest {
  private String identityDocumentType;
  private String identityDocumentNumber;
  private String legalName;
  private int page;
  private int size;
  private String sortBy;
  private String sortDir;
}

package com.wmc.guiaremision.application.dto;

import com.wmc.guiaremision.domain.model.enums.TipoDocumentoIdentidadEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFindAllRequest {
  private TipoDocumentoIdentidadEnum identityDocumentType;
  private String identityDocumentNumber;
  private String legalName;
  private int page;
  private int size;
  private String sortBy;
  private String sortDir;
}

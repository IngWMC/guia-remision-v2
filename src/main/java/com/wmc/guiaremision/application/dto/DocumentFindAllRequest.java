package com.wmc.guiaremision.application.dto;

import com.wmc.guiaremision.domain.model.enums.SunatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFindAllRequest {
  private String documentCode;
  private LocalDate startDate;
  private LocalDate endDate;
  private SunatStatusEnum statusSunat;
  private int page;
  private int size;
  private String sortBy;
  private String sortDir;
}

package com.wmc.guiaremision.infrastructure.web.dto.request;

import com.wmc.guiaremision.domain.model.enums.SunatStatusEnum;
import com.wmc.guiaremision.infrastructure.web.validation.EnumValid;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentFilterDto {

  private String documentCode;
  private LocalDate startDate;
  private LocalDate endDate;

  @EnumValid(enumClass = SunatStatusEnum.class)
  private String statusSunat;
}

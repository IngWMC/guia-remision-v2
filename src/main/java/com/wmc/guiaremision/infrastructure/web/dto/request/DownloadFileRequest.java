package com.wmc.guiaremision.infrastructure.web.dto.request;

import com.wmc.guiaremision.domain.model.enums.FileTypeEnum;
import com.wmc.guiaremision.infrastructure.web.validation.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DownloadFileRequest {
  @NotBlank
  private String requestId;

  @NotBlank
  @EnumValid(enumClass = FileTypeEnum.class)
  private String tipoArchivo;
}

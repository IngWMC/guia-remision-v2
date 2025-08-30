package com.wmc.guiaremision.domain.spi.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un archivo dentro de un ZIP
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZipFileResponse {
  private String filename;
  private String content;
}

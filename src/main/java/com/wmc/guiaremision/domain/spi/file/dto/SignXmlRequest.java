package com.wmc.guiaremision.domain.spi.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for XML digital signature operations.
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignXmlRequest {
  private String certificateBase64;
  private String certificatePassword;
  private String unsignedXmlContent;
  private Boolean singleExtensionNode;
}

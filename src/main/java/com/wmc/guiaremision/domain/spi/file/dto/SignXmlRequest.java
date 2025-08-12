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

  /**
   * Digital certificate content in base64 format.
   */
  private String digitalCertificate;

  /**
   * Password for the digital certificate.
   */
  private String certificatePassword;

  /**
   * XML content without signature to be signed.
   */
  private String unsignedXmlContent;

  /**
   * Flag to indicate if only one extension node should be used.
   */
  private Boolean singleExtensionNode;
}

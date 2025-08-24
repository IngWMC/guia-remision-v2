package com.wmc.guiaremision.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for document XML signature operations.
 * Contains application-specific data for signature requests.
 *
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignXmlDocumentRequest {
  private Integer documentId;
  private String unsignedXmlContent;
  private String signedXmlFilePath;
  private String certificateName;
  private String certificatePassword;
  private String certificateFilePath;
  private String identityDocumentNumber;
  private String documentType;
  private String documentCode;
}

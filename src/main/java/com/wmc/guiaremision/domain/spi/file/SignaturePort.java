package com.wmc.guiaremision.domain.spi.file;

import com.wmc.guiaremision.domain.spi.file.dto.SignXmlRequest;

/**
 * Port interface for XML digital signature operations.
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
public interface SignaturePort {

    /**
     * Digitally signs the provided XML content using the specified certificate.
     * 
     * @param signRequest The request containing certificate information and XML
     *                    content to sign
     * @return The XML content with the digital signature embedded
     */
    String signXml(SignXmlRequest signRequest);
}

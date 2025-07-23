package com.wmc.guiaremision.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Optional;
import org.w3c.dom.NodeList;

/**
 * Servicio para firma digital real de documentos electrónicos SUNAT
 * Implementa firma XML con certificados X.509
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DigitalSignatureService {
    
    @Value("${sunat.certificate.path}")
    private String certificatePath;
    
    @Value("${sunat.certificate.password}")
    private String certificatePassword;
    
    @Value("${sunat.certificate.alias}")
    private String certificateAlias;
    
    private final KeyStoreService keyStoreService;
    
    /**
     * Firma un documento XML con certificado digital
     */
    public String firmarDocumento(String xmlContent, String documentId) {
        return Optional.of(xmlContent)
                .map(this::parseXmlDocument)
                .map(doc -> firmarXmlDocument(doc, documentId))
                .map(this::convertToString)
                .orElseThrow(() -> new RuntimeException("Error al firmar documento"));
    }
    
    /**
     * Parsea el XML a Document
     */
    private org.w3c.dom.Document parseXmlDocument(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(xmlContent.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("Error al parsear XML", e);
            throw new RuntimeException("Error al parsear XML", e);
        }
    }
    
    /**
     * Firma el documento XML
     */
    private org.w3c.dom.Document firmarXmlDocument(org.w3c.dom.Document document, String documentId) {
        try {
            // Obtener certificado y clave privada
            KeyStore.PrivateKeyEntry privateKeyEntry = keyStoreService.getPrivateKeyEntry();
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            X509Certificate certificate = (X509Certificate) privateKeyEntry.getCertificate();
            
            // Crear contexto de firma
            XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
            Reference ref = factory.newReference("", factory.newDigestMethod(DigestMethod.SHA256, null));
            
            // Crear SignedInfo
            SignedInfo signedInfo = factory.newSignedInfo(
                factory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE),
                factory.newSignatureMethod(SignatureMethod.RSA_SHA256, null),
                Collections.singletonList(ref)
            );
            
            // Crear KeyInfo
            KeyInfoFactory keyInfoFactory = factory.getKeyInfoFactory();
            X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(certificate));
            KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
            
            // Crear firma XML
            XMLSignature signature = factory.newXMLSignature(signedInfo, keyInfo);
            
            // Firmar documento
            DOMSignContext signContext = new DOMSignContext(privateKey, document.getDocumentElement());
            signature.sign(signContext);
            
            log.info("Documento {} firmado exitosamente", documentId);
            return document;
            
        } catch (Exception e) {
            log.error("Error al firmar documento {}", documentId, e);
            throw new RuntimeException("Error al firmar documento", e);
        }
    }
    
    /**
     * Convierte Document a String
     */
    private String convertToString(org.w3c.dom.Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            log.error("Error al convertir documento a String", e);
            throw new RuntimeException("Error al convertir documento", e);
        }
    }
    
    /**
     * Valida la firma de un documento XML
     */
    public boolean validarFirma(String xmlContent) {
        return Optional.of(xmlContent)
                .map(this::parseXmlDocument)
                .map(this::validarFirmaDocumento)
                .orElse(false);
    }
    
    /**
     * Valida la firma del documento
     */
    private boolean validarFirmaDocumento(org.w3c.dom.Document document) {
        try {
            XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
            NodeList nodeList = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
            
            if (nodeList.getLength() == 0) {
                log.warn("No se encontró firma en el documento");
                return false;
            }
            
            XMLSignature signature = factory.unmarshalXMLSignature(
                new DOMValidateContext(keyStoreService.getPublicKey(), nodeList.item(0))
            );
            
            boolean isValid = signature.validate(new DOMValidateContext(keyStoreService.getPublicKey(), nodeList.item(0)));
            log.info("Validación de firma: {}", isValid);
            return isValid;
            
        } catch (Exception e) {
            log.error("Error al validar firma", e);
            return false;
        }
    }
    
    /**
     * Obtiene información del certificado
     */
    public CertificadoInfo obtenerInfoCertificado() {
        return Optional.of(keyStoreService.getPrivateKeyEntry())
                .map(entry -> (X509Certificate) entry.getCertificate())
                .map(this::crearCertificadoInfo)
                .orElseThrow(() -> new RuntimeException("Error al obtener información del certificado"));
    }
    
    /**
     * Crea información del certificado
     */
    private CertificadoInfo crearCertificadoInfo(X509Certificate certificate) {
        return CertificadoInfo.builder()
                .subject(certificate.getSubjectDN().getName())
                .issuer(certificate.getIssuerDN().getName())
                .serialNumber(certificate.getSerialNumber().toString())
                .validFrom(certificate.getNotBefore())
                .validTo(certificate.getNotAfter())
                .build();
    }
    
    /**
     * DTO para información del certificado
     */
    @lombok.Data
    @lombok.Builder
    public static class CertificadoInfo {
        private String subject;
        private String issuer;
        private String serialNumber;
        private java.util.Date validFrom;
        private java.util.Date validTo;
    }
} 
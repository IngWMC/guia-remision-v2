package com.wmc.guiaremision.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * Servicio para manejar KeyStore y certificados digitales SUNAT
 */
@Slf4j
@Service
public class KeyStoreService {
    
    @Value("${sunat.certificate.path}")
    private String certificatePath;
    
    @Value("${sunat.certificate.password}")
    private String certificatePassword;
    
    @Value("${sunat.certificate.alias}")
    private String certificateAlias;
    
    @Value("${sunat.certificate.type:PKCS12}")
    private String certificateType;
    
    private KeyStore keyStore;
    private KeyStore.PrivateKeyEntry privateKeyEntry;
    private PublicKey publicKey;
    
    /**
     * Inicializa el KeyStore
     */
    public void inicializarKeyStore() {
        try {
            keyStore = KeyStore.getInstance(certificateType);
            keyStore.load(new FileInputStream(certificatePath), certificatePassword.toCharArray());
            
            // Obtener entrada de clave privada
            privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(
                certificateAlias, 
                new KeyStore.PasswordProtection(certificatePassword.toCharArray())
            );
            
            // Obtener clave pública
            X509Certificate certificate = (X509Certificate) privateKeyEntry.getCertificate();
            publicKey = certificate.getPublicKey();
            
            log.info("KeyStore inicializado exitosamente");
            log.info("Certificado: {}", certificate.getSubjectDN());
            log.info("Válido desde: {} hasta: {}", 
                certificate.getNotBefore(), certificate.getNotAfter());
            
        } catch (Exception e) {
            log.error("Error al inicializar KeyStore", e);
            throw new RuntimeException("Error al inicializar KeyStore", e);
        }
    }
    
    /**
     * Obtiene la entrada de clave privada
     */
    public KeyStore.PrivateKeyEntry getPrivateKeyEntry() {
        if (privateKeyEntry == null) {
            inicializarKeyStore();
        }
        return privateKeyEntry;
    }
    
    /**
     * Obtiene la clave pública
     */
    public PublicKey getPublicKey() {
        if (publicKey == null) {
            inicializarKeyStore();
        }
        return publicKey;
    }
    
    /**
     * Obtiene el certificado X.509
     */
    public X509Certificate getCertificate() {
        return (X509Certificate) getPrivateKeyEntry().getCertificate();
    }
    
    /**
     * Obtiene la clave privada
     */
    public PrivateKey getPrivateKey() {
        return getPrivateKeyEntry().getPrivateKey();
    }
    
    /**
     * Verifica si el certificado está válido
     */
    public boolean esCertificadoValido() {
        try {
            X509Certificate certificate = getCertificate();
            certificate.checkValidity();
            return true;
        } catch (Exception e) {
            log.warn("Certificado no válido: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Obtiene información del certificado
     */
    public CertificadoInfo obtenerInfoCertificado() {
        X509Certificate certificate = getCertificate();
        return CertificadoInfo.builder()
                .subject(certificate.getSubjectDN().getName())
                .issuer(certificate.getIssuerDN().getName())
                .serialNumber(certificate.getSerialNumber().toString())
                .validFrom(certificate.getNotBefore())
                .validTo(certificate.getNotAfter())
                .esValido(esCertificadoValido())
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
        private boolean esValido;
    }
} 
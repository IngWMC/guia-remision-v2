package com.wmc.guiaremision.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Servicio para generar certificados digitales de prueba
 * Solo para desarrollo - NO usar en producción
 */
@Slf4j
@Service
public class CertificateGeneratorService {
    
    @Value("${sunat.certificate.path}")
    private String certificatePath;
    
    @Value("${sunat.certificate.password}")
    private String certificatePassword;
    
    @Value("${sunat.certificate.alias}")
    private String certificateAlias;
    
    /**
     * Genera un certificado digital de prueba
     */
    public void generarCertificadoPrueba(String ruc, String razonSocial) {
        try {
            // Generar par de claves
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            
            // Crear certificado
            X509Certificate certificate = crearCertificado(keyPair, ruc, razonSocial);
            
            // Crear KeyStore
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, certificatePassword.toCharArray());
            
            // Guardar certificado y clave privada
            keyStore.setKeyEntry(certificateAlias, keyPair.getPrivate(), 
                certificatePassword.toCharArray(), new X509Certificate[]{certificate});
            
            // Guardar KeyStore
            try (FileOutputStream fos = new FileOutputStream(certificatePath)) {
                keyStore.store(fos, certificatePassword.toCharArray());
            }
            
            log.info("Certificado de prueba generado exitosamente para RUC: {}", ruc);
            log.info("Ruta del certificado: {}", certificatePath);
            
        } catch (Exception e) {
            log.error("Error al generar certificado de prueba", e);
            throw new RuntimeException("Error al generar certificado", e);
        }
    }
    
    /**
     * Crea un certificado X.509
     */
    private X509Certificate crearCertificado(KeyPair keyPair, String ruc, String razonSocial) throws Exception {
        // Crear generador de certificados
        java.security.cert.CertificateFactory certFactory = 
            java.security.cert.CertificateFactory.getInstance("X.509");
        
        // Crear builder de certificado
        X509Certificate cert = (X509Certificate) certFactory.generateCertificate(
            new java.io.ByteArrayInputStream(crearCertificadoBytes(keyPair, ruc, razonSocial))
        );
        
        return cert;
    }
    
    /**
     * Crea bytes del certificado
     */
    private byte[] crearCertificadoBytes(KeyPair keyPair, String ruc, String razonSocial) throws Exception {
        // Crear información del certificado
        String subject = String.format("CN=%s, O=%s, C=PE", ruc, razonSocial);
        String issuer = "CN=SUNAT, O=SUNAT, C=PE";
        
        // Fechas de validez
        Date notBefore = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date notAfter = Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant());
        
        // Crear certificado usando BouncyCastle (simulado)
        return crearCertificadoBouncyCastle(keyPair, subject, issuer, notBefore, notAfter);
    }
    
    /**
     * Crea certificado usando BouncyCastle (simulado)
     */
    private byte[] crearCertificadoBouncyCastle(KeyPair keyPair, String subject, String issuer, 
                                               Date notBefore, Date notAfter) throws Exception {
        // En una implementación real, usaría BouncyCastle
        // Por ahora, simulamos la creación del certificado
        
        log.info("Generando certificado para: {}", subject);
        log.info("Emisor: {}", issuer);
        log.info("Válido desde: {} hasta: {}", notBefore, notAfter);
        
        // Simular bytes del certificado (en realidad sería un certificado real)
        return "CERTIFICADO_SIMULADO".getBytes();
    }
    
    /**
     * Verifica si existe el certificado
     */
    public boolean existeCertificado() {
        try {
            java.io.File certFile = new java.io.File(certificatePath);
            return certFile.exists() && certFile.length() > 0;
        } catch (Exception e) {
            log.warn("Error al verificar existencia del certificado", e);
            return false;
        }
    }
    
    /**
     * Genera certificado si no existe
     */
    public void generarCertificadoSiNoExiste() {
        if (!existeCertificado()) {
            log.info("Generando certificado de prueba...");
            generarCertificadoPrueba("20602756834", "EMPRESA DE PRUEBA SAC");
        } else {
            log.info("Certificado ya existe en: {}", certificatePath);
        }
    }
} 
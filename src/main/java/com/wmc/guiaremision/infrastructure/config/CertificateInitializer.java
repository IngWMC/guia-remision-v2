package com.wmc.guiaremision.infrastructure.config;

import com.wmc.guiaremision.infrastructure.security.CertificateGeneratorService;
import com.wmc.guiaremision.infrastructure.security.KeyStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de certificados digitales
 * Se ejecuta al arrancar la aplicación
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateInitializer implements CommandLineRunner {
    
    private final CertificateGeneratorService certificateGeneratorService;
    private final KeyStoreService keyStoreService;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Inicializando certificados digitales...");
        
        try {
            // Generar certificado de prueba si no existe
            certificateGeneratorService.generarCertificadoSiNoExiste();
            
            // Inicializar KeyStore
            keyStoreService.inicializarKeyStore();
            
            // Verificar validez del certificado
            if (keyStoreService.esCertificadoValido()) {
                log.info("✅ Certificado digital inicializado correctamente");
                
                // Mostrar información del certificado
                KeyStoreService.CertificadoInfo info = keyStoreService.obtenerInfoCertificado();
                log.info("📋 Información del certificado:");
                log.info("   - Subject: {}", info.getSubject());
                log.info("   - Issuer: {}", info.getIssuer());
                log.info("   - Serial Number: {}", info.getSerialNumber());
                log.info("   - Válido desde: {}", info.getValidFrom());
                log.info("   - Válido hasta: {}", info.getValidTo());
                log.info("   - Estado: {}", info.isEsValido() ? "VÁLIDO" : "NO VÁLIDO");
                
            } else {
                log.error("❌ Certificado digital no válido");
                throw new RuntimeException("Certificado digital no válido");
            }
            
        } catch (Exception e) {
            log.error("❌ Error al inicializar certificados digitales", e);
            throw new RuntimeException("Error al inicializar certificados", e);
        }
    }
} 
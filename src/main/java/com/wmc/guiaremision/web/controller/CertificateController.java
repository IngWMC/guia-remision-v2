package com.wmc.guiaremision.web.controller;

import com.wmc.guiaremision.infrastructure.security.DigitalSignatureService;
import com.wmc.guiaremision.infrastructure.security.KeyStoreService;
import com.wmc.guiaremision.infrastructure.security.CertificateGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador para gestión de certificados digitales
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/certificados")
@RequiredArgsConstructor
public class CertificateController {
    
    private final DigitalSignatureService digitalSignatureService;
    private final KeyStoreService keyStoreService;
    private final CertificateGeneratorService certificateGeneratorService;
    
    /**
     * Obtiene información del certificado actual
     */
    @GetMapping("/info")
    public ResponseEntity<DigitalSignatureService.CertificadoInfo> obtenerInfoCertificado() {
        return Optional.of(digitalSignatureService)
                .map(DigitalSignatureService::obtenerInfoCertificado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Verifica si el certificado está válido
     */
    @GetMapping("/validar")
    public ResponseEntity<ValidacionCertificadoDto> validarCertificado() {
        boolean esValido = keyStoreService.esCertificadoValido();
        return ResponseEntity.ok(ValidacionCertificadoDto.builder()
                .esValido(esValido)
                .mensaje(esValido ? "Certificado válido" : "Certificado no válido")
                .build());
    }
    
    /**
     * Genera un certificado de prueba
     */
    @PostMapping("/generar-prueba")
    public ResponseEntity<RespuestaGeneracionDto> generarCertificadoPrueba(
            @RequestBody GeneracionCertificadoDto request) {
        try {
            certificateGeneratorService.generarCertificadoPrueba(
                request.getRuc(), 
                request.getRazonSocial()
            );
            
            return ResponseEntity.ok(RespuestaGeneracionDto.builder()
                    .exito(true)
                    .mensaje("Certificado de prueba generado exitosamente")
                    .build());
                    
        } catch (Exception e) {
            log.error("Error al generar certificado de prueba", e);
            return ResponseEntity.badRequest().body(RespuestaGeneracionDto.builder()
                    .exito(false)
                    .mensaje("Error al generar certificado: " + e.getMessage())
                    .build());
        }
    }
    
    /**
     * Verifica si existe un certificado
     */
    @GetMapping("/existe")
    public ResponseEntity<ExistenciaCertificadoDto> verificarExistencia() {
        boolean existe = certificateGeneratorService.existeCertificado();
        return ResponseEntity.ok(ExistenciaCertificadoDto.builder()
                .existe(existe)
                .mensaje(existe ? "Certificado existe" : "Certificado no existe")
                .build());
    }
    
    /**
     * Valida la firma de un documento XML
     */
    @PostMapping("/validar-firma")
    public ResponseEntity<ValidacionFirmaDto> validarFirma(@RequestBody ValidarFirmaRequest request) {
        boolean firmaValida = digitalSignatureService.validarFirma(request.getXmlContent());
        return ResponseEntity.ok(ValidacionFirmaDto.builder()
                .firmaValida(firmaValida)
                .mensaje(firmaValida ? "Firma válida" : "Firma no válida")
                .build());
    }
    
    // DTOs para las respuestas
    @lombok.Data
    @lombok.Builder
    public static class ValidacionCertificadoDto {
        private boolean esValido;
        private String mensaje;
    }
    
    @lombok.Data
    @lombok.Builder
    public static class RespuestaGeneracionDto {
        private boolean exito;
        private String mensaje;
    }
    
    @lombok.Data
    @lombok.Builder
    public static class ExistenciaCertificadoDto {
        private boolean existe;
        private String mensaje;
    }
    
    @lombok.Data
    @lombok.Builder
    public static class ValidacionFirmaDto {
        private boolean firmaValida;
        private String mensaje;
    }
    
    @lombok.Data
    @lombok.Builder
    public static class GeneracionCertificadoDto {
        private String ruc;
        private String razonSocial;
    }
    
    @lombok.Data
    @lombok.Builder
    public static class ValidarFirmaRequest {
        private String xmlContent;
    }
} 
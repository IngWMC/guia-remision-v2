package com.wmc.guiaremision.infrastructure.adapter.file;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.wmc.guiaremision.domain.spi.file.StoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * Implementacion del puerto de almacenamiento usando Azure Blob Storage.
 * Solo se activa cuando el perfil 'azure' esta activo.
 *
 * Estructura de archivos en Blob Storage:
 * documents/
 * ├── {RUC_CLIENTE}/
 * │   ├── cdr/
 * │   ├── certificado/
 * │   ├── logo/
 * │   ├── pdf/
 * │   └── xml/
 * │       ├── firmado/
 * │       └── sin_firmar/
 *
 * @author Sistema GRE
 * @version 1.0
 */
@Slf4j
@Component
@Profile("azure")
@RequiredArgsConstructor
public class AzureBlobStorageAdapter implements StoragePort {

    private final BlobServiceClient blobServiceClient;

    @Value("${azure.storage.container-name:documents}")
    private String containerName;

    /**
     * Guarda un archivo en Azure Blob Storage.
     *
     * @param filePath        Ruta del directorio (ej: "20123456789/pdf/")
     * @param fileName        Nombre del archivo (ej: "T001-00000001.pdf")
     * @param contentInBase64 Contenido del archivo en Base64
     * @return true si el archivo se guardo correctamente
     * @throws IllegalArgumentException si el contenido esta vacio
     * @throws RuntimeException si ocurre un error al guardar
     */
    @Override
    public boolean saveFile(String filePath, String fileName, String contentInBase64) {
        try {
            // Validar contenido
            if (contentInBase64 == null || contentInBase64.trim().isEmpty()) {
                throw new IllegalArgumentException("El contenido del archivo no puede estar vacio");
            }

            // Validar parametros
            validateParameters(filePath, fileName);

            // Obtener o crear contenedor
            BlobContainerClient containerClient = getOrCreateContainer();

            // Construir ruta del blob: {filePath}/{fileName}
            // Ejemplo: 20123456789/pdf/T001-00000001.pdf
            String blobPath = buildBlobPath(filePath, fileName);

            // Obtener cliente del blob
            BlobClient blobClient = containerClient.getBlobClient(blobPath);

            // Decodificar Base64 y subir
            byte[] decodedContent = Base64.getDecoder().decode(contentInBase64);
            blobClient.upload(new ByteArrayInputStream(decodedContent), decodedContent.length, true);

            log.info("Archivo guardado en Azure Blob Storage: {}/{}", containerName, blobPath);
            return true;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al guardar archivo en Azure Blob Storage: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar el archivo: " + fileName, e);
        }
    }

    /**
     * Obtiene o crea el contenedor de Blob Storage.
     *
     * @return BlobContainerClient
     */
    private BlobContainerClient getOrCreateContainer() {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            containerClient.create();
            log.info("Contenedor creado: {}", containerName);
        }
        return containerClient;
    }

    /**
     * Construye la ruta del blob.
     *
     * @param filePath Ruta del directorio
     * @param fileName Nombre del archivo
     * @return Ruta completa del blob
     */
    private String buildBlobPath(String filePath, String fileName) {
        // Normalizar filePath (remover / inicial si existe)
        String normalizedPath = filePath.startsWith("/") ? filePath.substring(1) : filePath;

        // Asegurar que filePath termine con /
        if (!normalizedPath.endsWith("/")) {
            normalizedPath = normalizedPath + "/";
        }

        return normalizedPath + fileName;
    }

    /**
     * Valida los parametros de entrada.
     *
     * @param filePath Ruta del directorio
     * @param fileName Nombre del archivo
     * @throws IllegalArgumentException si los parametros son invalidos
     */
    private void validateParameters(String filePath, String fileName) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta del archivo no puede estar vacia");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede estar vacio");
        }
    }
}

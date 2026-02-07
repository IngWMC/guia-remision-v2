package com.wmc.guiaremision.infrastructure.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuracion de Azure Blob Storage.
 * Solo se activa cuando el perfil 'azure' esta activo.
 *
 * @author Sistema GRE
 * @version 1.0
 */
@Configuration
@Profile("azure")
public class AzureStorageConfig {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    /**
     * Crea el cliente de Azure Blob Storage.
     *
     * @return BlobServiceClient configurado
     */
    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();
    }
}

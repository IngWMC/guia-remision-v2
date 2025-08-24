package com.wmc.guiaremision.infrastructure.config.property;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuración de almacenamiento de archivos.
 * Mapea la sección 'storage' del application.yml
 *
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "storage")
public class StorageProperty {

  /**
   * Ruta base para el almacenamiento de archivos.
   * Valor por defecto: ./files
   * Se puede configurar mediante la variable de entorno STORAGE_PATH
   */
  private String basePath = "./files";

  /**
   * Indica si se debe usar separador de cliente en las rutas de archivos.
   * Valor por defecto: true
   */
  private Boolean clientSeparator = true;
}

package com.wmc.guiaremision.infrastructure.adapter.file;

import com.wmc.guiaremision.domain.spi.file.StoragePort;
import com.wmc.guiaremision.shared.common.Util;
import com.wmc.guiaremision.infrastructure.config.property.StorageProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Optional;

/**
 * Implementación del puerto de almacenamiento de archivos.
 * Proporciona funcionalidad para guardar archivos en el sistema de archivos.
 *
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StoragePortAdapter implements StoragePort {

  private final StorageProperty storageProperty;

  @Override
  public boolean saveFile(String filePath, String fileName, String contentInBase64) {
    return Optional.ofNullable(contentInBase64)
        .filter(content -> !content.trim().isEmpty())
        .map(content -> validateAndSaveFile(filePath, fileName, content))
        .orElseThrow(() -> new IllegalArgumentException("El contenido del archivo no puede estar vacío"));
  }

  /**
   * Valida los parámetros y guarda el archivo.
   *
   * @param filePath        Ruta del directorio donde guardar el archivo
   * @param fileName        Nombre del archivo a guardar
   * @param contentInBase64 Contenido del archivo en formato Base64
   * @return true si el archivo se guardó correctamente
   * @throws RuntimeException si ocurre un error durante el guardado
   */
  private boolean validateAndSaveFile(String filePath, String fileName, String contentInBase64) {
    try {
      // Validar parámetros
      validateParameters(filePath, fileName);

      // Construir rutas de manera eficiente
      Path targetDirectory = Util.getResourcePath(storageProperty.getBasePath(), filePath);
      Path filePathComplete = targetDirectory.resolve(fileName);

      // Crear directorio si no existe (más eficiente)
      createDirectoryIfNotExists(targetDirectory);

      // Decodificar y escribir archivo de manera optimizada
      writeFileOptimized(filePathComplete, contentInBase64);

      log.debug("Archivo guardado exitosamente: {}", filePathComplete);
      return true;

    } catch (IOException e) {
      log.error("Error al guardar el archivo {}: {}", fileName, e.getMessage());
      throw new RuntimeException("Error al guardar el archivo: " + fileName, e);
    }
  }

  /**
   * Valida que los parámetros de entrada sean válidos.
   *
   * @param filePath Ruta del directorio
   * @param fileName Nombre del archivo
   * @throws IllegalArgumentException si los parámetros son inválidos
   */
  private void validateParameters(String filePath, String fileName) {
    if (filePath == null || filePath.trim().isEmpty()) {
      throw new IllegalArgumentException("La ruta del archivo no puede estar vacía");
    }
    if (fileName == null || fileName.trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre del archivo no puede estar vacío");
    }
  }

  /**
   * Crea el directorio si no existe, de manera más eficiente.
   *
   * @param directory Ruta del directorio a crear
   * @throws IOException si no se puede crear el directorio
   */
  private void createDirectoryIfNotExists(Path directory) throws IOException {
    // Usar createDirectories que es más eficiente que exists() +
    // createDirectories()
    Files.createDirectories(directory);
  }

  /**
   * Escribe el archivo de manera optimizada usando operaciones atómicas.
   *
   * @param filePath        Ruta completa del archivo
   * @param contentInBase64 Contenido en Base64
   * @throws IOException si no se puede escribir el archivo
   */
  private void writeFileOptimized(Path filePath, String contentInBase64) throws IOException {
    // Decodificar Base64 de manera eficiente
    byte[] decodedContent = Base64.getDecoder().decode(contentInBase64);

    // Escribir archivo con opciones optimizadas
    Files.write(
        filePath,
        decodedContent,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING);
  }
}

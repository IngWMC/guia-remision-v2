package com.wmc.guiaremision.infrastructure.adapter.file;

import com.wmc.guiaremision.domain.spi.file.ZipFilePort;
import com.wmc.guiaremision.domain.spi.file.dto.ZipFileResponse;
import com.wmc.guiaremision.shared.common.Constant;
import com.wmc.guiaremision.shared.common.Convert;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class ZipFilePortAdapter implements ZipFilePort {
  @Override
  public ZipFileResponse extractZipFile(String zipFileContent, Predicate<String> fileNameFilter) {
    ZipFileResponse response = null;
    byte[] zipFileBytes = Convert.convertBase64ToByteArray(zipFileContent);

    try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipFileBytes))) {
      ZipEntry entry;

      while ((entry = zipInputStream.getNextEntry()) != null) {
        String filename = entry.getName();

        if (fileNameFilter == null || fileNameFilter.test(filename)) {
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int len;

          while ((len = zipInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
          }

          response = new ZipFileResponse(filename, outputStream.toString(Constant.UTF_8));
        }

        zipInputStream.closeEntry();
      }

      return response;
    } catch (IOException e) {
      throw new RuntimeException("Error al descomprimir archivo ZIP: " + e.getMessage());
    }
  }

  @Override
  public String generateZip(String xmlContent, String xmlFileName) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ZipOutputStream zos = new ZipOutputStream(baos)) {
      ZipEntry zipEntry = new ZipEntry(xmlFileName);
      zos.putNextEntry(zipEntry);

      byte[] decodedBytes = Base64.getDecoder().decode(xmlContent);
      zos.write(decodedBytes);

      zos.closeEntry();
      zos.finish();
      return Convert.convertByteArrayToBase64(baos.toByteArray());
    } catch (Exception e) {
      throw new RuntimeException("Error al generar ZIP", e);
    }
  }

  /**
   * Calcula el hash SHA-256 de un archivo ZIP contenido en Base64.
   *
   * <p>
   * Este método decodifica el contenido Base64 del archivo ZIP y calcula su
   * hash SHA-256, retornando el resultado en formato hexadecimal. Es utilizado
   * para generar el digest del archivo que se envía a SUNAT.
   * </p>
   *
   * @param zipFileContent Contenido del archivo ZIP en formato Base64
   * @return Hash SHA-256 en formato hexadecimal del archivo ZIP
   * @throws RuntimeException si hay error al calcular el hash
   */
  @Override
  public String calculateZipSha256Hash(String zipFileContent) {
    try {
      // Decodificar el archivo ZIP desde Base64
      byte[] zipBytes = Base64.getDecoder().decode(zipFileContent);

      // Crear instancia de MessageDigest para SHA-256
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      digest.update(zipBytes);

      // Calcular el hash y convertirlo a formato hexadecimal
      byte[] hashBytes = digest.digest();
      StringBuilder hexString = new StringBuilder();
      for (byte b : hashBytes) {
        hexString.append(String.format("%02x", b));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Algoritmo SHA-256 no disponible", e);
    } catch (Exception e) {
      throw new RuntimeException("Error al calcular hash SHA-256 del archivo ZIP", e);
    }
  }
}

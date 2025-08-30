package com.wmc.guiaremision.infrastructure.file;

import com.wmc.guiaremision.domain.spi.file.ZipFilePort;
import com.wmc.guiaremision.domain.spi.file.dto.ZipFileResponse;
import com.wmc.guiaremision.infrastructure.common.Constant;
import com.wmc.guiaremision.infrastructure.common.Convert;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class ZipFilePortImpl implements ZipFilePort {
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
}

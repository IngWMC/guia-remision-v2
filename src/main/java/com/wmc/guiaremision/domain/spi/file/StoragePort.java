package com.wmc.guiaremision.domain.spi.file;

public interface StoragePort {
  boolean saveFile(String filePath, String fileName, String contentInBase64);
}

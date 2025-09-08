package com.wmc.guiaremision.domain.spi.file;

import com.wmc.guiaremision.domain.spi.file.dto.ZipFileResponse;

import java.util.function.Predicate;

public interface ZipFilePort {
  ZipFileResponse extractZipFile(String zipFileContent, Predicate<String> fileNameFilter);
  String generateZip(String xmlContent, String xmlFileName);
  String calculateZipSha256Hash(String zipFileContent);
}

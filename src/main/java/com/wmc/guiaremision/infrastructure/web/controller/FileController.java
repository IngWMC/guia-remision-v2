package com.wmc.guiaremision.infrastructure.web.controller;

import com.wmc.guiaremision.application.service.FileService;
import com.wmc.guiaremision.domain.model.enums.FileTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/dispatch/v1/file")
@RequiredArgsConstructor
public class FileController {
  private final FileService fileService;

  @GetMapping("/download/{fileType}/{requestId}")
  public ResponseEntity<byte[]> file(@PathVariable() String fileType,
                                     @PathVariable() String requestId) {
    log.info("Descargando archivo. requestId: {}, fileType: {}", requestId, fileType);

    FileTypeEnum file = FileTypeEnum.fromCode(fileType);
    byte[] fileBytes = this.fileService.download(requestId, fileType);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.valueOf(file.getMimeType()));
    headers.setContentLength(fileBytes.length);
    headers.setContentDispositionFormData("attachment",
        requestId.concat(file.getExtension()));

    return ResponseEntity.ok()
        .headers(headers)
        .body(fileBytes);
  }
}

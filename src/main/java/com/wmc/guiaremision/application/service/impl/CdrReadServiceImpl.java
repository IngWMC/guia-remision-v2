package com.wmc.guiaremision.application.service.impl;

import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.AR;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.CAC;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.CBC;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.EXT;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.NODO_DESCRIPTION;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.NODO_DOCUMENT_REFERENCE;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.NODO_ID;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.NODO_NOTE;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.NODO_RESPONSE_CODE;

import com.wmc.guiaremision.application.dto.CdrDataResponse;
import com.wmc.guiaremision.application.service.CdrReadService;
import com.wmc.guiaremision.domain.spi.file.ZipFilePort;
import com.wmc.guiaremision.infrastructure.common.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CdrReadServiceImpl implements CdrReadService {

  private final ZipFilePort zipFilePort;

  /**
   * Extrae el contenido XML del CDR a partir del contenido del archivo ZIP.
   *
   * @param cdrFileContent Contenido del archivo ZIP en Base64.
   * @return Contenido XML extraído del CDR o una cadena vacía si no se encuentra.
   */
  @Override
  public String getCdrXmlContent(String cdrFileContent) {
    String xmlContent = this.zipFilePort
        .extractZipFile(cdrFileContent, fileNameFilter -> Constant.XML_EXTENSION
            .equalsIgnoreCase(this.getFileExtension(fileNameFilter)))
        .getContent();
    return xmlContent.isEmpty() ? "" : xmlContent;
  }

  /**
   * Extrae los datos relevantes del CDR a partir del contenido XML.
   *
   * @param cdrXmlContent Contenido XML del CDR.
   * @return Objeto CdrDataResponse con los datos extraídos.
   * @throws RuntimeException Si ocurre un error durante el procesamiento del XML.
   */
  @Override
  public CdrDataResponse getCdrData(String cdrXmlContent) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder builder = factory.newDocumentBuilder();

      Document doc = builder.parse(new InputSource(new StringReader(cdrXmlContent)));
      XPathFactory xPathFactory = XPathFactory.newInstance();
      XPath xpath = xPathFactory.newXPath();

      xpath.setNamespaceContext(this.getNamespaceContext());

      Node codeResponse = (Node) xpath.evaluate(NODO_RESPONSE_CODE, doc, XPathConstants.NODE);
      Node description = (Node) xpath.evaluate(NODO_DESCRIPTION, doc, XPathConstants.NODE);
      Node qrUrl = (Node) xpath.evaluate(NODO_DOCUMENT_REFERENCE, doc, XPathConstants.NODE);
      Node ticketNumber = (Node) xpath.evaluate(NODO_ID, doc, XPathConstants.NODE);
      Node note = (Node) xpath.evaluate(NODO_NOTE, doc, XPathConstants.NODE);

      return CdrDataResponse.builder()
          .code(codeResponse != null ? codeResponse.getTextContent() : null)
          .message(description != null ? description.getTextContent() : null)
          .qrUrl(qrUrl != null ? qrUrl.getTextContent() : null)
          .ticketNumber(ticketNumber != null ? ticketNumber.getTextContent() : null)
          .note(note != null ? note.getTextContent() : null)
          .build();
    } catch (Exception e) {
      throw new RuntimeException("Error al procesar datos del CDR: " + e.getMessage());
    }
  }

  /**
   * Obtiene la extensión de un archivo
   *
   * @param fileName Nombre del archivo
   * @return Extensión del archivo
   */
  private String getFileExtension(String fileName) {
    return Optional.ofNullable(fileName)
        .filter(name -> name.contains("."))
        .map(name -> name.substring(name.lastIndexOf(".")))
        .orElse("");
  }

  /**
   * Proporciona el contexto de namespaces para XPath
   *
   * @return NamespaceContext personalizado
   */
  private NamespaceContext getNamespaceContext() {
    return new NamespaceContext() {
      @Override
      public String getNamespaceURI(String prefix) {
        switch (prefix) {
          case "ar":
            return AR;
          case "cbc":
            return CBC;
          case "cac":
            return CAC;
          case "ext":
            return EXT;
          default:
            return null;
        }
      }

      @Override
      public String getPrefix(String uri) {
        return null;
      }

      @Override
      public java.util.Iterator<String> getPrefixes(String uri) {
        return null;
      }
    };
  }
}

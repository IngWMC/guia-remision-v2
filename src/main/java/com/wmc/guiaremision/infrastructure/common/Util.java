package com.wmc.guiaremision.infrastructure.common;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {
  /**
   * Genera una representación en formato XML de un objeto UBL.
   *
   * <p>
   * Este método utiliza JAXB para convertir objetos UBL a su representación
   * XML correspondiente, siguiendo los estándares UBL requeridos por la SUNAT.
   * </p>
   *
   * <p>
   * El XML generado incluye el formato apropiado y la codificación UTF-8
   * para garantizar la compatibilidad con los sistemas de la SUNAT.
   * </p>
   *
   * @param <T>    El tipo del objeto UBL que se desea convertir a XML.
   * @param clazz  La clase del objeto UBL que se desea convertir.
   * @param object El objeto UBL que se desea convertir a XML.
   * @return Una cadena de texto que contiene la representación XML del objeto
   *         UBL.
   * @throws RuntimeException Si ocurre un error durante la generación del XML,
   *                          generalmente debido a problemas de configuración
   *                          JAXB o estructura del objeto UBL.
   */
  public static <T> String generateXml(Class<T> clazz, T object) {
    try {
      JAXBContext context = JAXBContext.newInstance(clazz);
      Marshaller marshaller = context.createMarshaller();

      // Configuración básica de JAXB
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

      StringWriter writer = new StringWriter();
      marshaller.marshal(object, writer);

      String unsignedXml = replaceXmlContent(writer.toString());

      return Convert.convertByteArrayToBase64(unsignedXml.getBytes(StandardCharsets.UTF_8));
    } catch (JAXBException e) {
      throw new RuntimeException("Error al generar el XML de la guía de remisión", e);
    }
  }

  public static Path getResourcePath(String basePath, String filePath) {
    return Paths.get(basePath, filePath);
  }

  /**
   * Codifica una cadena para uso en URLs usando UTF-8.
   *
   * @param value Cadena a codificar
   * @return Cadena codificada para URL
   * @throws RuntimeException si hay error en la codificación
   */
  public static String encodeUrl(String value) {
    try {
      return URLEncoder.encode(value, Constant.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException("Error al codificar URL: " + value, e);
    }
  }

  public static String buildUrl(String routeName, String requestId) {
    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    return String.format("%s/%s/%s", baseUrl, routeName, requestId);
  }

  /**
   * Desescapa caracteres especiales en el contenido XML generado por JAXB.
   * 
   * <p>
   * Este método convierte las entidades HTML escapadas por JAXB de vuelta a sus
   * caracteres originales. Es útil para mostrar caracteres especiales como
   * ampersand (&), porcentaje (%), comillas y otros símbolos de forma legible
   * en el XML final.
   * </p>
   * 
   * @param xml El XML como String que contiene caracteres especiales escapados
   * @return El XML con los caracteres especiales desescapados
   * @since 1.0
   */
  private static String replaceXmlContent(String xml) {
    // Reemplazar standalone yes por no
    xml = xml.replace("standalone=\"yes\"", "standalone=\"no\"");

    // Reemplazar prefijo ns8 por elemento sin prefijo
    xml = xml.replaceAll("ns8:", "");
    xml = xml.replaceAll("xmlns:ns8", "xmlns");

    // Desescapar caracteres especiales comunes en contenido XML
    xml = xml.replaceAll("&amp;", "&");
    xml = xml.replaceAll("&#39;", "'");
    xml = xml.replaceAll("&#37;", "%");
    xml = xml.replaceAll("&#38;", "&");
    xml = xml.replaceAll("&lt;", "<");
    xml = xml.replaceAll("&gt;", ">");

    return xml;
  }
}

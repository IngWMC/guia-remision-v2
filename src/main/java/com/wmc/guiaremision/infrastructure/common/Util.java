package com.wmc.guiaremision.infrastructure.common;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
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

  public static String generateZip(String xmlContent, String xmlFileName) {
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
   * @param zipBase64Content Contenido del archivo ZIP en formato Base64
   * @return Hash SHA-256 en formato hexadecimal del archivo ZIP
   * @throws RuntimeException si hay error al calcular el hash
   */
  public static String calculateZipSha256Hash(String zipBase64Content) {
    try {
      // Decodificar el archivo ZIP desde Base64
      byte[] zipBytes = Base64.getDecoder().decode(zipBase64Content);

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

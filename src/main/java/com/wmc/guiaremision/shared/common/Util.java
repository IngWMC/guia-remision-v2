package com.wmc.guiaremision.shared.common;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;

import static com.wmc.guiaremision.shared.common.Constant.DATE_FORMAT;
import static com.wmc.guiaremision.shared.common.Constant.HOUR_FORMAT;
import static com.wmc.guiaremision.shared.common.Constant.SPACE;
import static com.wmc.guiaremision.shared.common.Constant.ZONE_ID;

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
      marshaller.setProperty(Marshaller.JAXB_ENCODING, Constant.UTF_8);

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

  public static String loadResourceFile(String basePath, String filePath, String fileName) {
    String resourcePath = Util.getResourcePath(basePath, filePath).toString();

    return Optional.of(new File(resourcePath, fileName))
        .filter(File::exists)
        .map(Util::readFileBytes)
        .filter(bytes -> bytes.length > 0)
        .map(Base64.getEncoder()::encodeToString)
        .orElseThrow(() -> new RuntimeException("No se pudo cargar el archivo."));
  }

  private static byte[] readFileBytes(File file) {
    try {
      return Files.readAllBytes(file.toPath());
    } catch (IOException e) {
      throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
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
      return URLEncoder.encode(value, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException("Error al codificar URL: " + value, e);
    }
  }

  /**
   * Construye una URL dinámica combinando la URL base con una ruta parametrizada.
   *
   * @param routeName Ruta con índices numerados (ejemplo: "/api/{0}/document/{1}")
   * @param params    Valores que reemplazarán los índices en orden
   * @return URL completa con los parámetros reemplazados
   */
  public static String buildUrl(String routeName, Object... params) {
    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    String formatString = baseUrl + routeName.replaceAll("\\{\\d+\\}", "%s");
    return String.format(formatString, params);
  }

  /**
   * Obtiene la fecha y hora actual en el formato específico para Perú.
   *
   * <p>
   * Este método genera una cadena con la fecha y hora actual utilizando la zona horaria
   * de Perú (definida en {@code ZONE_ID}). El formato resultante combina la fecha
   * ({@code DATE_FORMAT}) y la hora ({@code HOUR_FORMAT}) separados por un espacio.
   * </p>
   *
   * @return Cadena que representa la fecha y hora actual en el formato establecido
   *         para Perú
   * @see Constant#ZONE_ID
   * @see Constant#DATE_FORMAT
   * @see Constant#HOUR_FORMAT
   * @see Constant#SPACE
   */
  public static String getCurrentDateTime() {
    ZonedDateTime nowInPeru = ZonedDateTime.now(ZoneId.of(ZONE_ID));
    return nowInPeru.format(DateTimeFormatter.ofPattern(DATE_FORMAT
        .concat(SPACE)
        .concat(HOUR_FORMAT)));
  }

  /**
   * Obtiene la fecha y hora local actual en la zona horaria especificada.
   *
   * <p>
   * Este método devuelve un objeto {@link LocalDateTime} que representa la fecha
   * y hora actual en la zona horaria definida por {@code ZONE_ID}.
   * </p>
   *
   * @return Objeto {@link LocalDateTime} con la fecha y hora local actual
   *         en la zona horaria especificada
   * @see Constant#ZONE_ID
   */
  public static LocalDateTime getCurrentLocalDateTime() {
    return LocalDateTime.now(ZoneId.of(ZONE_ID));
  }

  /**
   * Obtiene el nombre de usuario del contexto de seguridad actual.
   *
   * <p>
   * Este método accede al contexto de seguridad proporcionado por Spring Security
   * para recuperar el nombre del usuario autenticado actualmente. Si no hay un
   * usuario autenticado, devuelve "SYSTEM" como valor predeterminado.
   * </p>
   *
   * @return El nombre de usuario del usuario autenticado, o "SYSTEM" si no hay
   *         usuario autenticado
   */
  public static String getCurrentUsername() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(Authentication::getName)
        .orElse("SYSTEM");
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

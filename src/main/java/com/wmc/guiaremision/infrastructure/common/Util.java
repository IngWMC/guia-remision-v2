package com.wmc.guiaremision.infrastructure.common;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

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
      // Crear el marshaller para convertir el objeto a XML
      Marshaller marshaller = context.createMarshaller();

      // Configuración básica de JAXB
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

      StringWriter writer = new StringWriter();
      marshaller.marshal(object, writer);
      String xml = writer.toString();

      // Post-procesamiento para corregir prefijos y standalone
      xml = xml.replace("standalone=\"yes\"", "standalone=\"no\"");

      // Reemplazar prefijo ns8 por elemento sin prefijo
      xml = xml.replaceAll("ns8:", "");
      xml = xml.replaceAll("xmlns:ns8", "xmlns");

      // Reemplazar caracteres especiales
      xml = xml.replaceAll("&amp;", "&");
      xml = xml.replaceAll("&#39;", "'");
      xml = xml.replaceAll("&#37;", "%");
      xml = xml.replaceAll("&#38;", "&");
      xml = xml.replaceAll("&lt;", "<");
      xml = xml.replaceAll("&gt;", ">");

      return xml;
    } catch (JAXBException e) {
      throw new RuntimeException("Error al generar el XML de la guía de remisión", e);
    }
  }
}

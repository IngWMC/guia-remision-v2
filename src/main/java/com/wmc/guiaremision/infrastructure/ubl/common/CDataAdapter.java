package com.wmc.guiaremision.infrastructure.ubl.common;

import java.util.Optional;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptador para generar secciones CDATA en XML.
 * 
 * <p>
 * Este adaptador genera CDATA real para evitar el escape de caracteres.
 * JAXB por defecto escapa caracteres especiales, pero con este adaptador
 * se generan secciones CDATA apropiadas.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
public class CDataAdapter extends XmlAdapter<String, String> {

  /**
   * Genera CDATA real para el valor.
   * 
   * @param v Valor a convertir a CDATA
   * @return Valor con sintaxis CDATA
   * @since 1.0
   */
  @Override
  public String marshal(String v) {
    return Optional.ofNullable(v)
        .map(value -> "<![CDATA[" + value + "]]>")
        .orElse(null);
  }

  /**
   * Deserializa el valor desde CDATA.
   * 
   * @param v Valor desde XML
   * @return El valor deserializado
   * @since 1.0
   */
  @Override
  public String unmarshal(String v) {
    return v;
  }
}
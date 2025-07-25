package com.wmc.guiaremision.infrastructure.ubl.common;

import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDataAdapter extends XmlAdapter<String, String> {
  @Override
  public String marshal(String v) {
    return Optional.ofNullable(v)
        .map(value -> "<![CDATA[" + value + "]]>")
        .orElse(null);
  }

  @Override
  public String unmarshal(String v) {
    return v;
  }
}
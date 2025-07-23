package com.wmc.guiaremision.ubl.basic.CommonBasicComponents;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.wmc.guiaremision.application.common.constant.UblAttributesConstant;
import lombok.Data;

/**
 * Tipo de documento de la guía de remisión (SUNAT exige '09')
 * ERROR 1050/1051
 */
@Data
public class DespatchAdviceTypeCode {

    @NotBlank(message = "El XML no contiene informacion en el tag DespatchAdviceTypeCode.")
    @Pattern(regexp = "09", message = "DespatchAdviceTypeCode - El valor del tipo de guía es inválido.")
    @XmlValue
    private String value = "09";

    @XmlAttribute(name = "listAgencyName")
    private String listAgencyName = UblAttributesConstant.agencyName;

    @XmlAttribute(name = "listName")
    private String listName = UblAttributesConstant.listName;

    @XmlAttribute(name = "listURI")
    private String listURI = UblAttributesConstant.listURI;

} 
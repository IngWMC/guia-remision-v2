package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Tipo de documento de la guía de remisión (SUNAT exige '09')
 * ERROR 1050/1051
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchAdviceTypeCode {
    @NotBlank(message = "El XML no contiene informacion en el tag DespatchAdviceTypeCode.")
    @Pattern(regexp = "09|31", message = "DespatchAdviceTypeCode - El valor del tipo de guía es inválido.")
    @XmlValue
    private String value;

    @XmlAttribute(name = "listAgencyName")
    private final String listAgencyName = UblAttributesConstant.agencyName;

    @XmlAttribute(name = "listName")
    private final String listName = UblAttributesConstant.listName;

    @XmlAttribute(name = "listURI")
    private final String listURI = UblAttributesConstant.listURI;
}
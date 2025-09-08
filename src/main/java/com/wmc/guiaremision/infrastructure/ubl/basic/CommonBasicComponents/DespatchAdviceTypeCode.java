package com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblAttributesConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Código del tipo de documento para guías de remisión.
 * 
 * <p>
 * Define el tipo de guía de remisión según los catálogos oficiales de SUNAT.
 * Valores permitidos: "09" (Guía de remisión remitente) y "31" (Guía de
 * remisión transportista).
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchAdviceTypeCode {
    /**
     * Valor del código de tipo de documento.
     * 
     * <p>
     * Valores: "09" o "31"
     * </p>
     */
    @XmlValue
    private String value;

    /**
     * Nombre de la agencia que mantiene la lista de códigos.
     */
    @XmlAttribute(name = "listAgencyName")
    private final String listAgencyName = UblAttributesConstant.agencyName;

    /**
     * Nombre de la lista de códigos.
     */
    @XmlAttribute(name = "listName")
    private final String listName = UblAttributesConstant.listName;

    /**
     * URI de la lista de códigos.
     */
    @XmlAttribute(name = "listURI")
    private final String listURI = UblAttributesConstant.listURI;
}
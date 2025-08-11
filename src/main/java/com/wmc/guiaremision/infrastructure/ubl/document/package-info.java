/**
 * Paquete para documentos UBL principales.
 * Define el namespace y prefijo para DespatchAdvice.
 */
@XmlSchema(namespace = DESPATCH_ADVICE, elementFormDefault = XmlNsForm.QUALIFIED, xmlns = {
    @XmlNs(prefix = "cac", namespaceURI = CAC),
    @XmlNs(prefix = "cbc", namespaceURI = CBC),
    @XmlNs(prefix = "ext", namespaceURI = EXT),
    @XmlNs(prefix = "ds", namespaceURI = DS),
    @XmlNs(prefix = "xsi", namespaceURI = XSI),
    @XmlNs(prefix = "xsd", namespaceURI = XSD),
    @XmlNs(prefix = "", namespaceURI = DESPATCH_ADVICE)
})
package com.wmc.guiaremision.infrastructure.ubl.document;

import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.CAC;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.CBC;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.DESPATCH_ADVICE;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.DS;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.EXT;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.XSD;
import static com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant.XSI;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;

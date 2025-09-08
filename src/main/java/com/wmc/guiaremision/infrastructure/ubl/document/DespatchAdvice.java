package com.wmc.guiaremision.infrastructure.ubl.document;

import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DeliveryCustomerParty;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DespatchLine;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.DespatchSupplierParty;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.Shipment;
import com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents.Signature;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.DespatchAdviceTypeCode;
import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import com.wmc.guiaremision.infrastructure.ubl.extension.CommonExtensionComponents.UblExtensions;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.ArrayList;

/**
 * Documento principal UBL DespatchAdvice para Guías de Remisión Electrónica.
 * 
 * <p>
 * Representa la estructura completa de una guía de remisión según el estándar
 * UBL 2.1
 * requerido por SUNAT para el envío de documentos electrónicos.
 * </p>
 * 
 * <p>
 * Incluye todos los elementos obligatorios y opcionales definidos en la
 * especificación
 * UBL para documentos de despacho, incluyendo datos del remitente,
 * destinatario,
 * información de envío y líneas de detalle.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "DespatchAdvice", namespace = UblNamespacesConstant.DESPATCH_ADVICE)
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchAdvice {

    /**
     * Extensiones UBL para funcionalidades adicionales.
     */
    @XmlElement(name = "UBLExtensions", namespace = UblNamespacesConstant.EXT)
    private UblExtensions ublExtensions = new UblExtensions();

    /**
     * Versión del estándar UBL utilizado.
     * 
     * <p>
     * Valor fijo "2.1" según especificación SUNAT.
     * </p>
     */
    @XmlElement(name = "UBLVersionID", namespace = UblNamespacesConstant.CBC)
    private String ublVersionId = "2.1";

    /**
     * Versión de la estructura del documento.
     * 
     * <p>
     * Valor fijo "2.0" según especificación SUNAT.
     * </p>
     */
    @XmlElement(name = "CustomizationID", namespace = UblNamespacesConstant.CBC)
    private String customizationId = "2.0";

    /**
     * Identificador único del documento.
     * 
     * <p>
     * Formato: Serie-Número correlativo (ej: T001-00000001)
     * </p>
     */
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    /**
     * Fecha de emisión del documento.
     * 
     * <p>
     * Formato: YYYY-MM-DD
     * </p>
     */
    @XmlElement(name = "IssueDate", namespace = UblNamespacesConstant.CBC)
    private String issueDate;

    /**
     * Hora de emisión del documento.
     * 
     * <p>
     * Formato: HH:MM:SS
     * </p>
     */
    @XmlElement(name = "IssueTime", namespace = UblNamespacesConstant.CBC)
    private String issueTime;

    /**
     * Código del tipo de documento.
     * 
     * <p>
     * Valores: "09" (Guía de remisión remitente), "31" (Guía de remisión
     * transportista)
     * </p>
     */
    @XmlElement(name = "DespatchAdviceTypeCode", namespace = UblNamespacesConstant.CBC)
    private DespatchAdviceTypeCode despatchAdviceTypeCode;

    /**
     * Observaciones adicionales del documento.
     * 
     * <p>
     * Campo opcional con máximo 250 caracteres.
     * </p>
     */
    @XmlElement(name = "Note", namespace = UblNamespacesConstant.CBC)
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String note;

    /**
     * Firma digital del documento.
     */
    @XmlElement(name = "Signature", namespace = UblNamespacesConstant.CAC)
    private Signature signature = new Signature();

    /**
     * Información del remitente de la mercancía.
     */
    @XmlElement(name = "DespatchSupplierParty", namespace = UblNamespacesConstant.CAC)
    private DespatchSupplierParty despatchSupplierParty = new DespatchSupplierParty();

    /**
     * Información del destinatario de la mercancía.
     */
    @XmlElement(name = "DeliveryCustomerParty", namespace = UblNamespacesConstant.CAC)
    private DeliveryCustomerParty deliveryCustomerParty = new DeliveryCustomerParty();

    /**
     * Información del envío y transporte.
     */
    @XmlElement(name = "Shipment", namespace = UblNamespacesConstant.CAC)
    private Shipment shipment = new Shipment();

    /**
     * Líneas de detalle de la guía de remisión.
     */
    @XmlElement(name = "DespatchLine", namespace = UblNamespacesConstant.CAC)
    private List<DespatchLine> despatchLines = new ArrayList<>();
}
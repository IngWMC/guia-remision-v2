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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Clase principal para generar documentos UBL DespatchAdvice (Guía de Remisión)
 * Basada en la estructura UBL 2.1 para SUNAT
 * Validaciones según tabla oficial SUNAT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "DespatchAdvice", namespace = UblNamespacesConstant.DESPATCH_ADVICE)
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchAdvice {

    // Extensiones UBL
    @XmlElement(name = "UBLExtensions", namespace = UblNamespacesConstant.EXT)
    private UblExtensions ublExtensions = new UblExtensions();

    /**
     * Versión UBL (Obligatorio, debe ser '2.1')
     * ERROR 2111/2110
     */
    @NotBlank(message = "El XML no contiene el tag o no existe informacion de UBLVersionID")
    @Pattern(regexp = "2\\.1", message = "UBLVersionID - La versión del UBL no es correcta")
    @XmlElement(name = "UBLVersionID", namespace = UblNamespacesConstant.CBC)
    private String ublVersionId = "2.1";

    /**
     * Versión de la estructura del documento (Obligatorio, debe ser '2.0')
     * ERROR 2113/2112
     */
    @NotBlank(message = "El XML no contiene el tag o no existe informacion de CustomizationID")
    @Pattern(regexp = "2\\.0", message = "CustomizationID - La version del documento no es correcta")
    @XmlElement(name = "CustomizationID", namespace = UblNamespacesConstant.CBC)
    private String customizationId = "2.0";

    /**
     * Numeración: Serie-Número correlativo (Obligatorio, formato T###-NNNNNNNN)
     * ERROR 1001/1035/1036/1033/1032
     */
    @NotBlank(message = "ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante")
    @Pattern(regexp = "[T][A-Z0-9]{3}-[0-9]{1,8}", message = "ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante")
    @Size(max = 13, message = "ID - El dato SERIE-CORRELATIVO no cumple con el formato de acuerdo al tipo de comprobante")
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    /**
     * Fecha de emisión (Obligatorio, formato YYYY-MM-DD)
     * ERROR 3436/2108/2329
     */
    @NotBlank(message = "El campo de fecha de emision no cumple con el formato establecido")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "El campo de fecha de emision no cumple con el formato establecido")
    @XmlElement(name = "IssueDate", namespace = UblNamespacesConstant.CBC)
    private String issueDate;

    /**
     * Hora de emisión (Obligatorio, formato hh:mm:ss)
     * ERROR 3437/3438
     */
    @NotBlank(message = "No existe informacion en el campo de hora de emision")
    @Pattern(regexp = "\\d{2}:\\d{2}:\\d{2}", message = "El campo de hora de emision no cumple con el formato establecido")
    @XmlElement(name = "IssueTime", namespace = UblNamespacesConstant.CBC)
    private String issueTime;

    /**
     * Tipo de documento (Guía) (Obligatorio, valor '09')
     * ERROR 1050/1051
     */
    @NotNull(message = "El XML no contiene informacion en el tag DespatchAdviceTypeCode.")
    @XmlElement(name = "DespatchAdviceTypeCode", namespace = UblNamespacesConstant.CBC)
    private DespatchAdviceTypeCode despatchAdviceTypeCode;

    /**
     * Observaciones (Texto, opcional, hasta 250 caracteres)
     * OBSERV 4186
     */
    @Size(max = 250, message = "cbc:Note - El campo observaciones supera la cantidad maxima especificada (250 carácteres).")
    @XmlElement(name = "Note", namespace = UblNamespacesConstant.CBC)
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String note;

    // Firma digital
    @XmlElement(name = "Signature", namespace = UblNamespacesConstant.CAC)
    private Signature signature = new Signature();

    // TODO: Revisar si es necesario gregar << OrderReference >>

    // TODO: Revisar si es necesario gregar << AdditionalDocumentReference >>

    /**
     * Datos del Remitente
     */
    @XmlElement(name = "DespatchSupplierParty", namespace = UblNamespacesConstant.CAC)
    private DespatchSupplierParty despatchSupplierParty;

    /**
     * Datos del Destinatario
     */
    @XmlElement(name = "DeliveryCustomerParty", namespace = UblNamespacesConstant.CAC)
    private DeliveryCustomerParty deliveryCustomerParty;

    // Información de envío
    @XmlElement(name = "Shipment", namespace = UblNamespacesConstant.CAC)
    private Shipment shipment;

    // Líneas de guía de remisión
    @NotNull(message = "Debe existir al menos una línea de despacho")
    @XmlElement(name = "DespatchLine", namespace = UblNamespacesConstant.CAC)
    private List<DespatchLine> despatchLines = new ArrayList<>();

    /**
     * Constructor con valores por defecto
     */
    public DespatchAdvice(String id, LocalDateTime issueDateTime) {
        this.id = id;
        this.issueDate = issueDateTime.toLocalDate().toString();
        this.issueTime = issueDateTime.toLocalTime().toString();
        this.ublExtensions = new UblExtensions();
        this.signature = new Signature();
        this.despatchLines = new ArrayList<>();
    }
}
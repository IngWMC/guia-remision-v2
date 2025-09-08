package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;

import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.GrossWeightMeasure;
import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.HandlingCode;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Información de envío para documentos UBL.
 * 
 * <p>
 * Contiene toda la información relacionada con el traslado de mercancías,
 * incluyendo motivo de traslado, modalidad de transporte, peso, fechas
 * y detalles del transporte.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Shipment {
    /**
     * Identificador único del envío.
     * 
     * <p>
     * Código que identifica de manera única este envío específico.
     * </p>
     */
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    /**
     * Código del motivo de traslado.
     * 
     * <p>
     * Especifica el motivo por el cual se realiza el traslado según
     * los catálogos oficiales de SUNAT.
     * </p>
     */
    @XmlElement(name = "HandlingCode", namespace = UblNamespacesConstant.CBC)
    private HandlingCode handlingCode = new HandlingCode();

    /**
     * Instrucciones o descripción del motivo de traslado.
     * 
     * <p>
     * Información adicional que complementa el código de motivo de traslado.
     * </p>
     */
    @XmlElement(name = "HandlingInstructions", namespace = UblNamespacesConstant.CBC)
    private String handlingInstructions;

    /**
     * Peso bruto total de la carga.
     * 
     * <p>
     * Peso total de todas las mercancías incluidas en el envío,
     * incluyendo embalajes y contenedores.
     * </p>
     */
    @XmlElement(name = "GrossWeightMeasure", namespace = UblNamespacesConstant.CBC)
    private GrossWeightMeasure grossWeightMeasure = new GrossWeightMeasure();

    /**
     * Etapa del envío.
     * 
     * <p>
     * Información sobre la etapa específica del transporte y
     * modalidad utilizada.
     * </p>
     */
    @XmlElement(name = "ShipmentStage", namespace = UblNamespacesConstant.CAC)
    private ShipmentStage shipmentStage = new ShipmentStage();

    /**
     * Información de entrega.
     * 
     * <p>
     * Detalles sobre la entrega de las mercancías, incluyendo
     * fechas, direcciones y condiciones.
     * </p>
     */
    @XmlElement(name = "Delivery", namespace = UblNamespacesConstant.CAC)
    private Delivery delivery = new Delivery();

    /**
     * Unidad de manejo de transporte.
     * 
     * <p>
     * Información sobre contenedores, pallets u otras unidades
     * de manejo utilizadas en el transporte.
     * </p>
     */
    @XmlElement(name = "TransportHandlingUnit", namespace = UblNamespacesConstant.CAC)
    private TransportHandlingUnit transportHandlingUnit;
}
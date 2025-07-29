package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.GrossWeightMeasure;
import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.HandlingCode;
import lombok.Data;

/**
 * Información de envío (Shipment) para la guía de remisión SUNAT
 * Incluye validaciones de motivo de traslado, modalidad, fechas, peso, unidad,
 * etc.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Shipment {
    /**
     * Identificador del traslado (Obligatorio, valor fijo SUNAT_Envio)
     */
    @NotBlank(message = "El XML no contiene el tag o no existe informacion del ID del traslado")
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    /**
     * Motivo de traslado (Obligatorio, Catálogo N° 20, 2 caracteres)
     * ERROR 3404/3405
     */
    @NotBlank(message = "El XML no contiene el tag o no existe informacion del motivo de traslado")
    @XmlElement(name = "HandlingCode", namespace = UblNamespacesConstant.CBC)
    private HandlingCode handlingCode;

    /**
     * Descripción de motivo de traslado (Condicional, hasta 100 caracteres)
     * ERROR 3457/4190
     */
    @Size(max = 100, message = "El valor ingresado como descripcion de motivo de traslado no cumple con el estandar.")
    @XmlElement(name = "HandlingInstructions", namespace = UblNamespacesConstant.CBC)
    private String handlingInstructions;

    /**
     * Peso bruto total de la carga (Obligatorio, decimal positivo de 12 enteros y 3
     * decimales)
     * ERROR 2880/2523
     */
    @NotNull(message = "Es obligatorio ingresar el peso bruto total de la guía")
    @XmlElement(name = "GrossWeightMeasure", namespace = UblNamespacesConstant.CBC)
    private GrossWeightMeasure grossWeightMeasure;

    @XmlElement(name = "ShipmentStage", namespace = UblNamespacesConstant.CAC)
    private ShipmentStage shipmentStage = new ShipmentStage();

    /**
     * Información de entrega (Delivery) de la guía de remisión.
     * Obligatorio según SUNAT.
     */
    @NotNull(message = "El bloque Delivery es obligatorio")
    @XmlElement(name = "Delivery", namespace = UblNamespacesConstant.CAC)
    private Delivery delivery;

    @XmlElement(name = "TransportHandlingUnit", namespace = UblNamespacesConstant.CAC)
    private TransportHandlingUnit transportHandlingUnit;
}
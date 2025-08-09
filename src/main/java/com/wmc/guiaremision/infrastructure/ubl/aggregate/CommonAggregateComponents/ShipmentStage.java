package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.TransportModeCode;
import lombok.Data;

/**
 * Etapa de envío (ShipmentStage) para UBL/SUNAT
 * Incluye transportista, conductores, entidad emisora de autorización, periodo de tránsito y eventos de transporte
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentStage {
    /**
     * Modalidad de traslado (Obligatorio, Catálogo N° 18, 2 caracteres)
     * ERROR 2532/2773
     */
    @NotBlank(message = "El XML no contiene el tag o no existe informacion de la modalidad de traslado")
    @XmlElement(name = "TransportModeCode", namespace = UblNamespacesConstant.CBC)
    private TransportModeCode transportModeCode = new TransportModeCode();

    /**
     * Fecha de inicio de traslado (Obligatorio, formato YYYY-MM-DD)
     * ERROR 3406/3343/3407
     */
    @NotBlank(message = "El XML no contiene el tag o no existe informacion de la fecha de inicio de traslado o fecha de entrega del bien al Transportista")
    @XmlElement(name = "TransitPeriod", namespace = UblNamespacesConstant.CBC)
    private TransitPeriod transitPeriod = new TransitPeriod();

    /**
     * Transportista (CarrierParty)
     */
    @XmlElement(name = "CarrierParty", namespace = UblNamespacesConstant.CAC)
    private CarrierParty carrierParty = new CarrierParty();

    /**
     * Conductores (DriverPerson), principal
     */
    @XmlElement(name = "DriverPerson", namespace = UblNamespacesConstant.CAC)
    private DriverPerson driverPerson = new DriverPerson();

} 
package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import com.wmc.guiaremision.infrastructure.ubl.basic.CommonBasicComponents.TransportModeCode;
import lombok.Data;

/**
 * Etapa de envío para documentos UBL.
 * 
 * <p>
 * Representa una etapa específica del proceso de transporte de mercancías,
 * incluyendo información sobre la modalidad de transporte, fechas de tránsito,
 * transportista responsable y conductor del vehículo.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentStage {
    /**
     * Código de modalidad de transporte.
     * 
     * <p>
     * Especifica el medio de transporte utilizado en esta etapa del envío
     * según los catálogos oficiales de SUNAT (terrestre, aéreo, marítimo, etc.).
     * </p>
     */
    @XmlElement(name = "TransportModeCode", namespace = UblNamespacesConstant.CBC)
    private TransportModeCode transportModeCode = new TransportModeCode();

    /**
     * Período de tránsito.
     * 
     * <p>
     * Define las fechas de inicio y fin del traslado en esta etapa,
     * permitiendo el seguimiento temporal del proceso de transporte.
     * </p>
     */
    @XmlElement(name = "TransitPeriod", namespace = UblNamespacesConstant.CAC)
    private TransitPeriod transitPeriod = new TransitPeriod();

    /**
     * Entidad transportista responsable.
     * 
     * <p>
     * Información de la empresa o entidad responsable del transporte
     * en esta etapa específica del envío.
     * </p>
     */
    @XmlElement(name = "CarrierParty", namespace = UblNamespacesConstant.CAC)
    private CarrierParty carrierParty;

    /**
     * Conductor del vehículo.
     * 
     * <p>
     * Datos del conductor responsable del vehículo en esta etapa
     * del transporte, incluyendo identificación y licencia.
     * </p>
     */
    @XmlElement(name = "DriverPerson", namespace = UblNamespacesConstant.CAC)
    private DriverPerson driverPerson;

}
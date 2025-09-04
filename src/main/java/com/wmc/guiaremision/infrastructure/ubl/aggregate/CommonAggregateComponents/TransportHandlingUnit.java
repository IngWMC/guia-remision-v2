package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import java.util.List;

/**
 * Unidad de manipulación de transporte (TransportHandlingUnit) para UBL/SUNAT
 * Incluye vehículo principal y hasta dos vehículos secundarios
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportHandlingUnit {
    /**
     * Vehículo principal (TransportEquipment)
     */
    @NotNull(message = "Debe especificar el vehículo principal (TransportEquipment)")
    @Valid
    @XmlElement(name = "TransportEquipment", namespace = UblNamespacesConstant.CAC)
    private TransportEquipment transportEquipment = new TransportEquipment();

    /**
     * Vehículos secundarios (AttachedTransportEquipment), máximo 2
     */
    @Valid
    @XmlElement(name = "AttachedTransportEquipment")
    private List<TransportEquipment> attachedTransportEquipments;
} 
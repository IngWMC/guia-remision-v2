package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
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
    private TransportEquipment transportEquipment;

    /**
     * Vehículos secundarios (AttachedTransportEquipment), máximo 2
     */
    @Valid
    @XmlElement(name = "AttachedTransportEquipment")
    private List<TransportEquipment> attachedTransportEquipments;
} 
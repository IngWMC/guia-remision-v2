package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Equipo de transporte (TransportEquipment) para UBL/SUNAT
 * Incluye placa, TUC/certificado, autorización, con validaciones y enums
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportEquipment {
    /**
     * Placa del vehículo (an..8, obligatorio, validación SUNAT)
     * ERROR 2566, 2567, 4398
     */
    @NotBlank(message = "La placa del vehículo es obligatoria")
    @Size(min = 6, max = 8, message = "La placa debe tener entre 6 y 8 caracteres")
    @Pattern(regexp = "^[A-Z0-9]{6,8}$", message = "La placa solo permite letras mayúsculas y números, sin espacios ni guiones")
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    @XmlElement(name = "ApplicableTransportMeans", namespace = UblNamespacesConstant.CBC)
    private ApplicableTransportMeans applicableTransportMeans;

    @XmlElement(name = "ShipmentDocumentReference", namespace = UblNamespacesConstant.CBC)
    private ShipmentDocumentReference shipmentDocumentReference;
} 
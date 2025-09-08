package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import java.util.List;

/**
 * Unidad de manejo de transporte para documentos UBL.
 * 
 * <p>
 * Representa el conjunto de vehículos utilizados para el transporte de
 * mercancías,
 * incluyendo el vehículo principal y los vehículos secundarios o remolques
 * que forman parte de la unidad de transporte.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportHandlingUnit {
    /**
     * Equipo de transporte principal.
     * 
     * <p>
     * Vehículo principal responsable del transporte de las mercancías,
     * incluyendo información de placa, certificados y autorizaciones.
     * </p>
     */
    @XmlElement(name = "TransportEquipment", namespace = UblNamespacesConstant.CAC)
    private TransportEquipment transportEquipment = new TransportEquipment();

    /**
     * Equipos de transporte adjuntos.
     * 
     * <p>
     * Lista de vehículos secundarios, remolques o equipos adicionales
     * que forman parte de la unidad de transporte, con un máximo
     * de dos equipos adjuntos.
     * </p>
     */
    @XmlElement(name = "AttachedTransportEquipment")
    private List<TransportEquipment> attachedTransportEquipments;
}
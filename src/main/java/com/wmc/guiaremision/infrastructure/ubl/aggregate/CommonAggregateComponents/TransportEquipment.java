package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Equipo de transporte para documentos UBL.
 * 
 * <p>
 * Representa la información del vehículo o equipo utilizado para el transporte
 * de mercancías, incluyendo placa, certificados y autorizaciones necesarias
 * para el traslado.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportEquipment {
    /**
     * Identificador del equipo de transporte.
     * 
     * <p>
     * Placa o número de identificación del vehículo utilizado para el transporte
     * de las mercancías, según las regulaciones de tránsito vigentes.
     * </p>
     */
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    /**
     * Medio de transporte aplicable.
     * 
     * <p>
     * Información sobre el medio de transporte específico utilizado,
     * incluyendo TUC (Tarjeta Única de Circulación) o certificados
     * correspondientes.
     * </p>
     */
    @XmlElement(name = "ApplicableTransportMeans", namespace = UblNamespacesConstant.CBC)
    private ApplicableTransportMeans applicableTransportMeans;

    /**
     * Referencia a documento de envío.
     * 
     * <p>
     * Documento de autorización o permiso relacionado con el equipo
     * de transporte, como autorizaciones especiales o permisos de circulación.
     * </p>
     */
    @XmlElement(name = "ShipmentDocumentReference", namespace = UblNamespacesConstant.CBC)
    private ShipmentDocumentReference shipmentDocumentReference;
}
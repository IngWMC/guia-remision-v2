package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Medio de transporte aplicable para documentos UBL.
 * 
 * <p>
 * Especifica información sobre el medio de transporte utilizado para el
 * traslado
 * de mercancías, incluyendo el TUC (Tarjeta Única de Circulación) o certificado
 * del vehículo.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicableTransportMeans {
    /**
     * TUC o certificado del vehículo de transporte.
     * 
     * <p>
     * Identificador único del vehículo según la Tarjeta Única de Circulación
     * o certificado correspondiente.
     * </p>
     */
    @XmlElement(name = "RegistrationNationalityID")
    private String registrationNationalityId;
}
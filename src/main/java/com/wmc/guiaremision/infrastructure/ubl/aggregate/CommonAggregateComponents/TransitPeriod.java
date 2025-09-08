package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

/**
 * Período de tránsito para documentos UBL.
 * 
 * <p>
 * Define el período temporal durante el cual se realiza el traslado de
 * mercancías,
 * incluyendo las fechas de inicio y fin del proceso de transporte.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TransitPeriod {
    /**
     * Fecha de inicio del traslado.
     * 
     * <p>
     * Fecha en la que se inicia el traslado de las mercancías desde el punto
     * de origen, expresada en formato YYYY-MM-DD.
     * </p>
     */
    @XmlElement(name = "StartDate", namespace = UblNamespacesConstant.CBC)
    private String startDate;
}
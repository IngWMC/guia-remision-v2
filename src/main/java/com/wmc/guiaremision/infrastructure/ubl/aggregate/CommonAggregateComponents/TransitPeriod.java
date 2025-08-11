package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

/**
 * Periodo de tránsito (TransitPeriod) para UBL/SUNAT
 * Incluye fecha de inicio de traslado
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TransitPeriod {
    /**
     * Fecha de inicio de traslado (YYYY-MM-DD, obligatorio)
     * ERROR 3406, 3343, 3407
     */
    @NotBlank(message = "La fecha de inicio de traslado es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato YYYY-MM-DD")
    @XmlElement(name = "StartDate", namespace = UblNamespacesConstant.CBC)
    private String startDate;
} 
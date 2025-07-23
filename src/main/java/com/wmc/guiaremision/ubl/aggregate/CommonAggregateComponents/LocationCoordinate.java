package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.Data;

/**
 * Coordenada de ubicación (LocationCoordinate) para UBL/SUNAT
 * Incluye latitud y longitud, con validaciones
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationCoordinate {
    /**
     * Latitud (LatitudeDegreesMeasure, n(3,8), acepta positivos y negativos)
     */
    @Pattern(regexp = "^-?\\d{1,3}(\\.\\d{1,8})?$", message = "La latitud debe ser numérica, hasta 3 enteros y 8 decimales")
    @XmlElement(name = "LatitudeDegreesMeasure", namespace = UblNamespacesConstant.CBC)
    private String latitudeDegreesMeasure;

    /**
     * Longitud (LongitudeDegreesMeasure, n(3,8), acepta positivos y negativos)
     */
    @Pattern(regexp = "^-?\\d{1,3}(\\.\\d{1,8})?$", message = "La longitud debe ser numérica, hasta 3 enteros y 8 decimales")
    @XmlElement(name = "LongitudeDegreesMeasure", namespace = UblNamespacesConstant.CBC)
    private String longitudeDegreesMeasure;
} 
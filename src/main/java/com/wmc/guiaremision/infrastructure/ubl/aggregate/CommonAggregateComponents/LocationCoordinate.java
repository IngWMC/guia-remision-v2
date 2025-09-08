package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Coordenadas geográficas para documentos UBL.
 * 
 * <p>
 * Representa la ubicación geográfica precisa de un punto específico mediante
 * coordenadas de latitud y longitud, permitiendo la geolocalización exacta
 * de direcciones de partida, llegada o cualquier ubicación relevante.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationCoordinate {
    /**
     * Medida de latitud en grados.
     * 
     * <p>
     * Coordenada de latitud que especifica la posición norte-sur del punto,
     * expresada en grados decimales. Valores positivos para el hemisferio norte
     * y negativos para el hemisferio sur.
     * </p>
     */
    @XmlElement(name = "LatitudeDegreesMeasure", namespace = UblNamespacesConstant.CBC)
    private String latitudeDegreesMeasure;

    /**
     * Medida de longitud en grados.
     * 
     * <p>
     * Coordenada de longitud que especifica la posición este-oeste del punto,
     * expresada en grados decimales. Valores positivos para el hemisferio este
     * y negativos para el hemisferio oeste.
     * </p>
     */
    @XmlElement(name = "LongitudeDegreesMeasure", namespace = UblNamespacesConstant.CBC)
    private String longitudeDegreesMeasure;
}
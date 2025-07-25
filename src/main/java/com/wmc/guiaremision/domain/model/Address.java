package com.wmc.guiaremision.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una dirección física utilizada en la Guía de Remisión Electrónica
 * (GRE).
 * Incluye información detallada sobre la ubicación geográfica, como distrito,
 * provincia, departamento y código de ubigeo.
 *
 * <p>
 * Esta clase forma parte del modelo de dominio y se utiliza para describir
 * tanto la dirección de partida como de llegada en los documentos de remisión.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /** Dirección completa (calle, número, etc.). */
    private String direccion;

    /** Nombre del distrito correspondiente a la dirección. */
    private String distrito;

    /** Nombre de la provincia correspondiente a la dirección. */
    private String provincia;

    /** Nombre del departamento correspondiente a la dirección. */
    private String departamento;

    /** Código de ubigeo (código geográfico oficial del Perú) de la dirección. */
    private String codigoUbigeo;
}
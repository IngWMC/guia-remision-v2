package com.wmc.guiaremision.domain.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import lombok.Data;

/**
 * Dirección (Address) para UBL/SUNAT
 * Incluye validaciones de ubigeo, dirección detallada, atributos scheme y país.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
    /**
     * Dirección completa y detallada (Obligatorio, 3-500 caracteres, sin solo espacios)
     * ERROR 2577/4076/2574/4068
     */
    @NotBlank(message = "El XML no contiene el tag o no existe informacion de direccion detallada de punto de partida/llegada.")
    @Size(min = 3, max = 500, message = "Direccion de punto de partida/llegada - El dato ingresado no cumple con el formato establecido.")
    @Pattern(regexp = "^(?!\\s*$)[^\n\r\t]*$", message = "Direccion de punto de partida/llegada - El dato ingresado no cumple con el formato establecido.")
    @XmlElement(name = "Line")
    private String fullAddress;

    /**
     * Ubigeo (Obligatorio, 6 dígitos)
     * ERROR 2775/2776/3363/3368
     */
    @NotBlank(message = "El XML no contiene el atributo o no existe informacion del codigo de ubigeo.")
    @Pattern(regexp = "\\d{6}", message = "El valor ingresado como codigo de ubigeo no cumple con el estandar.")
    @XmlElement(name = "ID")
    private String ubigeo;

    /**
     * Código de país (opcional, 2 caracteres ISO)
     */
    @Size(min = 2, max = 2, message = "El código de país debe tener 2 caracteres ISO")
    @XmlElement(name = "CountryCode")
    private String countryCode;

    // Otros atributos relevantes pueden agregarse aquí (scheme, provincia, departamento, distrito, etc.)
} 
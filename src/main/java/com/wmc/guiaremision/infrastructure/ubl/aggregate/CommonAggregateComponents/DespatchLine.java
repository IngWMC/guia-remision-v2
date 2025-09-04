package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.wmc.guiaremision.infrastructure.ubl.common.Quantity;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Línea de la guía de remisión (SUNAT)
 * Incluye validaciones de número de orden, cantidad, unidad, descripción, código, etc.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchLine {
    /**
     * Número de orden del ítem (Obligatorio, numérico hasta 4 dígitos)
     * ERROR 2023/2752
     */
    @NotBlank(message = "El Numero de orden del item no cumple con el formato establecido")
    @Pattern(regexp = "\\d{1,4}", message = "El Numero de orden del item no cumple con el formato establecido")
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    /**
     * Cantidad del bien (Obligatorio, decimal positivo de 12 enteros y hasta 10 decimales)
     * ERROR 2580/2780
     */
    @NotNull(message = "El XML No contiene el tag o no existe información de la cantidad del item.")
    @XmlElement(name = "DeliveredQuantity", namespace = UblNamespacesConstant.CBC)
    private Quantity deliveredQuantity = new Quantity();

    /**
     * Número de orden referencial del ítem (Obligatorio, numérico hasta 4 dígitos)
     */
    @XmlElement(name = "OrderLineReference", namespace = UblNamespacesConstant.CAC)
    private OrderLineReference orderLineReference = new OrderLineReference();

    /**
     * Detalle del bien (descripción, código, etc.)
     */
    @NotNull(message = "El item debe tener información de producto")
    @XmlElement(name = "Item", namespace = UblNamespacesConstant.CAC)
    private DespatchLineItem item = new DespatchLineItem();

} 
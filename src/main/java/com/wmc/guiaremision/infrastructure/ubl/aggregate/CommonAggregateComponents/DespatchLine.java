package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.common.Quantity;
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
     * Número de orden referencial del ítem (Obligatorio, numérico hasta 4 dígitos)
     */
    @XmlElement(name = "OrderLineReference", namespace = UblNamespacesConstant.CAC)
    private OrderLineReference orderLineReference = new OrderLineReference();

    /**
     * Cantidad del bien (Obligatorio, decimal positivo de 12 enteros y hasta 10 decimales)
     * ERROR 2580/2780
     */
    @NotNull(message = "El XML No contiene el tag o no existe información de la cantidad del item.")
    @XmlElement(name = "DeliveredQuantity", namespace = UblNamespacesConstant.CBC)
    private Quantity deliveredQuantity = new Quantity();

    /**
     * Detalle del bien (descripción, código, etc.)
     */
    @NotNull(message = "El item debe tener información de producto")
    @XmlElement(name = "Item", namespace = UblNamespacesConstant.CAC)
    private DespatchLineItem item = new DespatchLineItem();

} 
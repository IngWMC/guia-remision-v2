package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;

import com.wmc.guiaremision.infrastructure.ubl.common.Quantity;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * Línea de detalle de la guía de remisión.
 * 
 * <p>
 * Representa cada línea individual de productos o servicios incluidos en la
 * guía de remisión, conteniendo información sobre cantidades, referencias
 * de pedido y detalles del ítem.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchLine {
    /**
     * Identificador único de la línea.
     * 
     * <p>
     * Número secuencial que identifica cada línea de la guía de remisión.
     * </p>
     */
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    /**
     * Cantidad entregada del producto o servicio.
     * 
     * <p>
     * Especifica la cantidad real que se está despachando, incluyendo
     * la unidad de medida correspondiente.
     * </p>
     */
    @XmlElement(name = "DeliveredQuantity", namespace = UblNamespacesConstant.CBC)
    private Quantity deliveredQuantity = new Quantity();

    /**
     * Referencia a la línea de pedido correspondiente.
     * 
     * <p>
     * Establece la relación con la línea específica del pedido que
     * originó este despacho.
     * </p>
     */
    @XmlElement(name = "OrderLineReference", namespace = UblNamespacesConstant.CAC)
    private OrderLineReference orderLineReference = new OrderLineReference();

    /**
     * Información detallada del producto o servicio.
     * 
     * <p>
     * Contiene la descripción, códigos, identificadores y demás
     * información específica del ítem despachado.
     * </p>
     */
    @XmlElement(name = "Item", namespace = UblNamespacesConstant.CAC)
    private DespatchLineItem item = new DespatchLineItem();

}
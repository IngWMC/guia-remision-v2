package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.PartyIdentificationId;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Identificación de una entidad (Party).
 * 
 * <p>
 * Contiene el identificador único de una entidad junto con el esquema
 * utilizado para su validación según los estándares UBL.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification", propOrder = { "id" })
public class PartyIdentification {
    /**
     * Identificador único de la entidad.
     */
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private PartyIdentificationId id = new PartyIdentificationId();
}
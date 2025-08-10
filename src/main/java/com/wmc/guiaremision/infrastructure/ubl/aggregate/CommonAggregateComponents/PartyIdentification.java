package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.PartyIdentificationId;
import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification", propOrder = { "id" })
public class PartyIdentification {
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private PartyIdentificationId id = new PartyIdentificationId();
}
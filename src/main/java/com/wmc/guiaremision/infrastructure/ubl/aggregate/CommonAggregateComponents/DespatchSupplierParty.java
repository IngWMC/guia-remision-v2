package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import javax.xml.bind.annotation.*;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

/**
 * Datos del remitente
 * Incluye identificación y razón social del transportista, con validaciones SUNAT
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchSupplierParty {

    @XmlElement(name = "Party", namespace = UblNamespacesConstant.CAC)
    private Party party;

} 
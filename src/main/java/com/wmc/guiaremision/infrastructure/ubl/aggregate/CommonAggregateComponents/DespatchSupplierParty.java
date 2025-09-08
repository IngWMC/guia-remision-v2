package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import lombok.Data;

/**
 * Entidad remitente para documentos UBL.
 * 
 * <p>
 * Representa la entidad que envía o despacha las mercancías desde el origen,
 * incluyendo su identificación legal y datos de contacto.
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DespatchSupplierParty {

    /**
     * Información completa de la entidad remitente.
     * 
     * <p>
     * Contiene todos los datos de la entidad que despacha las mercancías,
     * incluyendo identificación, razón social y datos legales.
     * </p>
     */
    @XmlElement(name = "Party", namespace = UblNamespacesConstant.CAC)
    private Party party = new Party();

}
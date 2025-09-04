package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import lombok.Data;

/**
 * Entidad legal de la parte (PartyLegalEntity) para UBL/SUNAT
 * Incluye razón social del transportista
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PartyLegalEntity {
    /**
     * Razón social del transportista (an..250, obligatorio)
     * ERROR 1037, 4338
     */
    @Size(max = 250, message = "La razón social no debe superar los 250 caracteres")
    @XmlElement(name = "RegistrationName", namespace = UblNamespacesConstant.CBC)
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String registrationName;

    /**
     * Número de Registro MTC
     */
    @XmlElement(name = "CompanyID", namespace = UblNamespacesConstant.CBC)
    private String companyId;
}
package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.common.constant.UblNamespacesConstant;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import lombok.Data;

/**
 * Transportista (CarrierParty) para UBL/SUNAT
 * Incluye entidad legal con número de registro MTC
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CarrierParty {
    @NotNull(message = "Debe especificar el tipo de documento del transportista (PartyIdentification)")
    @XmlElement(name = "PartyIdentification", namespace = UblNamespacesConstant.CAC)
    private PartyIdentification partyIdentification;

    /**
     * Razon Social del transportista (incluye CompanyID: Registro MTC)
     */
    @NotNull(message = "Debe especificar la entidad legal del transportista (PartyLegalEntity)")
    @Valid
    @XmlElement(name = "PartyLegalEntity", namespace = UblNamespacesConstant.CAC)
    private PartyLegalEntity partyLegalEntity;
} 
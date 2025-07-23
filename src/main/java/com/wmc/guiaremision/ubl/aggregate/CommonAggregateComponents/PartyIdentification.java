package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.application.common.constant.UblAttributesConstant;
import com.wmc.guiaremision.application.common.constant.UblNamespacesConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification", propOrder = { "id" })
public class PartyIdentification {
    @XmlElement(name = "ID", namespace = UblNamespacesConstant.CBC)
    private String id;

    @XmlAttribute(name = "schemeURI")
    private String schemeURI = UblAttributesConstant.schemeUri;

    @XmlAttribute(name = "schemeAgencyName")
    private String schemeAgencyName = UblAttributesConstant.schemeAgencyName;

    @XmlAttribute(name = "schemeName")
    private String schemeName = UblAttributesConstant.schemeName;

    @XmlAttribute(name = "schemeID")
    private String schemeID = "6";
}
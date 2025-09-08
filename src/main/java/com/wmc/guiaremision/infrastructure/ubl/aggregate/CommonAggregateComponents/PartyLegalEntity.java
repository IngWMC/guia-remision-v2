package com.wmc.guiaremision.infrastructure.ubl.aggregate.CommonAggregateComponents;

import com.wmc.guiaremision.infrastructure.ubl.common.constant.UblNamespacesConstant;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.wmc.guiaremision.infrastructure.ubl.common.CDataAdapter;
import lombok.Data;

/**
 * Entidad legal de una parte para documentos UBL.
 * 
 * <p>
 * Representa la información legal de una entidad, incluyendo su razón social
 * y registros oficiales como el MTC (Ministerio de Transportes y
 * Comunicaciones).
 * </p>
 * 
 * @author Sistema GRE
 * @version 1.0
 * @since 1.0
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PartyLegalEntity {
    /**
     * Razón social o nombre legal de la entidad.
     * 
     * <p>
     * Nombre oficial registrado de la empresa o entidad, con soporte
     * para caracteres especiales mediante CDATA.
     * </p>
     */
    @XmlElement(name = "RegistrationName", namespace = UblNamespacesConstant.CBC)
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String registrationName;

    /**
     * Identificador de la empresa.
     * 
     * <p>
     * Número de registro oficial de la empresa, como el registro MTC
     * para empresas de transporte.
     * </p>
     */
    @XmlElement(name = "CompanyID", namespace = UblNamespacesConstant.CBC)
    private String companyId;
}
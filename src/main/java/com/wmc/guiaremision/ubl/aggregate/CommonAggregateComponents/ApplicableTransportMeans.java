package com.wmc.guiaremision.ubl.aggregate.CommonAggregateComponents;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import lombok.Data;

/**
 * Medio de transporte aplicable (ApplicableTransportMeans) para UBL/SUNAT
 * Incluye TUC/certificado
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicableTransportMeans {
    /**
     * TUC/Certificado (an..15, obligatorio)
     * ERROR 3355, 4399, 4400
     */
    @NotBlank(message = "El número de TUC/certificado es obligatorio")
    @Size(min = 10, max = 15, message = "El número de TUC/certificado debe tener entre 10 y 15 caracteres")
    @Pattern(regexp = "^[A-Z0-9]{10,15}$", message = "El número de TUC/certificado solo permite letras mayúsculas y números")
    @XmlElement(name = "RegistrationNationalityID")
    private String registrationNationalityId;
} 
package com.wmc.guiaremision.domain.model;

import lombok.Data;

@Data
public class Contributor {
    private String documentNumber;
    private String documentType;
    private String legalName;
    private String commercialName;
    private String ubigeo;
    private String fullAddress;
    private String urbanization;
    private String province;
    private String department;
    private String district;
    private String countryCode;
} 
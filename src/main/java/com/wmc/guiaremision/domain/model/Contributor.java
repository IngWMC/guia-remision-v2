package com.wmc.guiaremision.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Contributor {
    private String identityDocumentType;
    private String identityDocumentNumber;
    private String legalName;
    private String commercialName;
    private String mtcNumber;
    private Address address;
} 
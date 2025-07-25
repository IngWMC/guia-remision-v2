package com.wmc.guiaremision.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RelatedDocument {
    private String documentType;
    private String documentNumber;
    private String issueDate;
    private String series;
    private String sequenceNumber;
} 
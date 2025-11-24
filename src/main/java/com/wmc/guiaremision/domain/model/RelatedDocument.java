package com.wmc.guiaremision.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatedDocument {
    private String documentType;
    private String documentNumber;
    private String issueDate;
    private String series;
    private String sequenceNumber;
}
package com.wmc.guiaremision.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DispatchDetail {
    private String description;
    private Integer quantity;
    private String unitOfMeasure;
    private String weight;
    private String productCode;
    private String sunatCode;
}
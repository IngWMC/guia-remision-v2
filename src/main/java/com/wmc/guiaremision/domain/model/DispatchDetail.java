package com.wmc.guiaremision.domain.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DispatchDetail {
    private String code;
    private String description;
    private String unitOfMeasure;
    private BigDecimal quantity;
    private String internationalCode;
} 
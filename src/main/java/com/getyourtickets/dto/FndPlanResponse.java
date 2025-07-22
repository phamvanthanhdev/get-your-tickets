package com.getyourtickets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.getyourtickets.model.FndPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FndPlanResponse implements Serializable {
    private Integer planId;
    private String planCode;
    private String planName;
    private BigDecimal salePrice;
    private String createdDate;
    private String updatedDate;

    public static FndPlanResponse from(FndPlan fndPlan) {
        return FndPlanResponse.builder()
                .planId(fndPlan.getId())
                .planCode(fndPlan.getCode())
                .planName(fndPlan.getNameEn())
                .salePrice(fndPlan.getSalePrice())
                .createdDate(fndPlan.getCreatedAt())
                .updatedDate(fndPlan.getUpdatedAt())
                .build();
    }
}

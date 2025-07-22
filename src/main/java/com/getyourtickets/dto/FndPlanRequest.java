package com.getyourtickets.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FndPlanRequest {
    private String nameEn;
    private BigDecimal salePrice;

    public Map<String, Object> toMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("nameEn", this.nameEn);
        params.put("salePrice", this.salePrice);
        return params;
    }
}

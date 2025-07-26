package com.getyourtickets.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 255, message = "INVALID_FND_PLAN_NAME")
    private String nameEn;
    @Min(value = 1000, message = "INVALID_FND_PLAN_SALE_PRICE")
    private BigDecimal salePrice;

    public Map<String, Object> toMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("nameEn", this.nameEn);
        params.put("salePrice", this.salePrice);
        return params;
    }
}

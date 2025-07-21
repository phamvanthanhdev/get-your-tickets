package com.getyourtickets.dto;

import com.getyourtickets.model.FndPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class FndPlanResponse implements Serializable {
    private int planId;
    private String planName;
    private BigDecimal salePrice;
    private String createdDate;
    private String updatedDate;

    public FndPlanResponse() {
    }

    public FndPlanResponse(int planId, String planName, BigDecimal salePrice, String createdDate, String updatedDate) {
        this.planId = planId;
        this.planName = planName;
        this.salePrice = salePrice;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public static FndPlanResponse from(FndPlan fndPlan) {
        return new FndPlanResponse(
                fndPlan.getId(),
                fndPlan.getNameEn(),
                fndPlan.getSalePrice(),
                fndPlan.getCreatedAt(),
                fndPlan.getUpdatedAt()
        );
    }
}

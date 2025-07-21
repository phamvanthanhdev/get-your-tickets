package com.getyourtickets.model;

import java.math.BigDecimal;

public class FndPlan {
    private int id;
    private String nameEn;
    private BigDecimal salePrice;
    private String createdAt;
    private String updatedAt;

    public FndPlan() {
    }

    public FndPlan(int id, String nameEn, BigDecimal salePrice, String createdAt, String updatedAt) {
        this.id = id;
        this.nameEn = nameEn;
        this.salePrice = salePrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

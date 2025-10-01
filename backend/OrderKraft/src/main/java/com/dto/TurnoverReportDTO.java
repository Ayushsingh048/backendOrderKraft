package com.dto;

public class TurnoverReportDTO {
    private Long productId;
    private String productName;
    private String categoryName;
    private Long unitsSold;
    private Double avgHoldingDays;
    private Double turnoverRatio;

    // --- Getters & Setters ---
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Long getUnitsSold() { return unitsSold; }
    public void setUnitsSold(Long unitsSold) { this.unitsSold = unitsSold; }

    public Double getAvgHoldingDays() { return avgHoldingDays; }
    public void setAvgHoldingDays(Double avgHoldingDays) { this.avgHoldingDays = avgHoldingDays; }

    public Double getTurnoverRatio() { return turnoverRatio; }
    public void setTurnoverRatio(Double turnoverRatio) { this.turnoverRatio = turnoverRatio; }
}

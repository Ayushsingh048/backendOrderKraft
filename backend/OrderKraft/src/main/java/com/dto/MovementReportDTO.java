package com.dto;

import java.time.LocalDate;

public class MovementReportDTO {
    private LocalDate movementDate;
    private Long productId;
    private String productName;
    private Integer inQty;
    private Integer outQty;
    private Integer balanceQty;

    public MovementReportDTO() {
        this.movementDate = movementDate;
        this.productId = productId;
        this.productName = productName;
        this.inQty = inQty;
        this.outQty = outQty;
        this.balanceQty = balanceQty;
    }

    // getters and setters
    public LocalDate getMovementDate() {
        return movementDate;
    }
    public void setMovementDate(LocalDate movementDate) {
        this.movementDate = movementDate;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getInQty() {
        return inQty;
    }
    public void setInQty(Integer inQty) {
        this.inQty = inQty;
    }
    public Integer getOutQty() {
        return outQty;
    }
    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }
    public Integer getBalanceQty() {
        return balanceQty;
    }
    public void setBalanceQty(Integer balanceQty) {
        this.balanceQty = balanceQty;
    }
}

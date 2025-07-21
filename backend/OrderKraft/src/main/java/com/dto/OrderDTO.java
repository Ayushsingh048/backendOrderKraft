package com.dto;

import java.time.LocalDate;

public class OrderDTO {
    private Long id;
    private LocalDate orderDate;
    private String status;
    private Long totalAmount;
    private Long procurementOfficerId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getProcurementOfficerId() {
        return procurementOfficerId;
    }

    public void setProcurementOfficerId(Long procurementOfficerId) {
        this.procurementOfficerId = procurementOfficerId;
    }
}

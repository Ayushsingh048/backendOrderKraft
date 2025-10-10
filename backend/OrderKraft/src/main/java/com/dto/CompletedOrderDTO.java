package com.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CompletedOrderDTO {

    private long order_id;                    // same style as your DTOs
    private String order_name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate order_date;
    
    private String status;
    private BigDecimal total_amount;
    
    // from order_item table
    private long quantity;
    private BigDecimal unit_price;

    // --- Constructors ---
    public CompletedOrderDTO() {}

    public CompletedOrderDTO(long order_id, String order_name, LocalDate order_date,
                             String status, BigDecimal total_amount,
                             long quantity, BigDecimal unit_price) {
        this.order_id = order_id;
        this.order_name = order_name;
        this.order_date = order_date;
        this.status = status;
        this.total_amount = total_amount;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    // --- Getters and Setters ---
    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }
}

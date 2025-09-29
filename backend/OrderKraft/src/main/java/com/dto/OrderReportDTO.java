package com.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderReportDTO {

    private long orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate deliveryDate;

    private String status;
    private BigDecimal totalAmount;
    private String orderName;
    private long supplierId;
    private String procurementOfficerName; // mapped from Order.procurementOfficer
    private List<Order_ItemDTO> items;

    public OrderReportDTO() {}

    public OrderReportDTO(long orderId, LocalDate orderDate, LocalDate deliveryDate, String status,
                          BigDecimal totalAmount, String orderName, long supplierId,
                          String procurementOfficerName, List<Order_ItemDTO> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderName = orderName;
        this.supplierId = supplierId;
        this.procurementOfficerName = procurementOfficerName;
        this.items = items;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getProcurementOfficerName() {
        return procurementOfficerName;
    }

    public void setProcurementOfficerName(String procurementOfficerName) {
        this.procurementOfficerName = procurementOfficerName;
    }

    public List<Order_ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<Order_ItemDTO> items) {
        this.items = items;
    }
}

package com.dto;
 
import com.enums.ReturnReason;
import java.math.BigDecimal;
import java.time.LocalDate;
 
public class ReturnRequestResponseDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private double unitPrice;
    private Integer quantity;
    private ReturnReason reason;
    private String comment;
    private LocalDate requestDate;
    private String status;
 
    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
 
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
 
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
 
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
 
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
 
    public ReturnReason getReason() { return reason; }
    public void setReason(ReturnReason reason) { this.reason = reason; }
 
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
 
    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }
 
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
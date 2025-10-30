package com.dto;

import com.enums.ReturnReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReturnRequestDTO {

    @NotNull
    private Long orderId;

    @NotNull
    private ReturnReason reason;

    @Size(max = 500)
    private String comment;

    // optional: specify which product is being returned (falls back to order.productId if null)
    private Long productId;

    // optional: how many units to return (falls back to 1 if null)
    private Integer quantity;

    // optional: supplier id (falls back to order.supplierId if null)
    private Long supplierId;
    private Long orderItemId;   // NEW
    
    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public ReturnReason getReason() { return reason; }
    public void setReason(ReturnReason reason) { this.reason = reason; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    
    public Long getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }

}

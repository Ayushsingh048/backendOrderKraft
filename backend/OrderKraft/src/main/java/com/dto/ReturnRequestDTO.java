package com.dto;

import com.enums.ReturnReason;

public class ReturnRequestDTO {
    private Long orderId;
    private Long productId;    // NEW
    private Integer quantity;  // NEW
    private ReturnReason reason;
    private String comment;

    // getters / setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public ReturnReason getReason() { return reason; }
    public void setReason(ReturnReason reason) { this.reason = reason; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}

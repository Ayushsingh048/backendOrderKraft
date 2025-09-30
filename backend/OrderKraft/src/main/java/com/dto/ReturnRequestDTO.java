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


// NEW optional fields to request a return for a specific product/quantity
private Long productId; // optional: the product being returned
private Integer quantity; // optional: how many units to return


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
}
package com.dto;

public class UpdateOrderDTO {
    private Long orderId;       // clearer naming than "id"
    private Long orderItemId;   // which order item to update
    private Long quantity;
    private String name;
    private String status;   

    public UpdateOrderDTO() {}

    public UpdateOrderDTO(Long orderId, Long orderItemId, Long quantity, String name, String status) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.name = name;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus()  { 
    	return status;
    }   
    public void setStatus(String status) { 
    	this.status = status; 
    }
}

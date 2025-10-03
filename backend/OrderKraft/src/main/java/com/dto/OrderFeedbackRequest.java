package com.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrderFeedbackRequest {

    @NotNull
    private Long orderId;

    @Min(0) @Max(5)
    private int communication;

    @Min(0) @Max(5)
    private int delivery;

    @Min(0) @Max(5)
    private int quality;

    @Size(max = 2000)
    private String feedback;

    // Getters & Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public int getCommunication() { return communication; }
    public void setCommunication(int communication) { this.communication = communication; }

    public int getDelivery() { return delivery; }
    public void setDelivery(int delivery) { this.delivery = delivery; }

    public int getQuality() { return quality; }
    public void setQuality(int quality) { this.quality = quality; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}

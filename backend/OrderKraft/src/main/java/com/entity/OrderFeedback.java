package com.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_feedbacks")
public class OrderFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
	@SequenceGenerator(name = "feedback_seq", sequenceName = "ORDER_FEEDBACKS_SEQ", allocationSize = 1)
	private Long id;
//    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private int communication;

    @Column(nullable = false)
    private int delivery;

    @Column(nullable = false)
    private int quality;

    @Column(length = 2000)
    private String feedback;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

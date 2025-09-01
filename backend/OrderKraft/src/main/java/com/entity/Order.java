package com.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "orders")
public class Order {
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_seq")
	    @SequenceGenerator(name = "Order_seq", sequenceName = "Order_seq", allocationSize = 1)
    private long orderId;

    @Column(name = "order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_amount", scale = 2)
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "procurement_officer_id")
    private User procurementOfficer;

    public Order() {
    }

    //  Getters and Setters

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

    public User getProcurementOfficer() {
        return procurementOfficer;
    }

    public void setProcurementOfficer(User user) {
        this.procurementOfficer = user;
    }
}

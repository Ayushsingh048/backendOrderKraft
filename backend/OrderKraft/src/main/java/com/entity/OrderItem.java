
package com.entity;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderItem_seq")
	    @SequenceGenerator(name = "OrderItem_seq", sequenceName = "OrderItem_seq", allocationSize = 1)
	 
    private long orderItemId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "unit_price", scale = 2)
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonBackReference
    private Order order;
    
    @Column(name = "name")
    private String name;

    public OrderItem() {
    }

    // ✅ Getters and Setters

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(long l) {
        this.quantity = l;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

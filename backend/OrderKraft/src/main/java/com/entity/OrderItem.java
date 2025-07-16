
package com.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderItem_seq")
	    @SequenceGenerator(name = "OrderItem_seq", sequenceName = "OrderItem_seq", allocationSize = 1)
    private String orderItemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public OrderItem() {
    }

    // ✅ Getters and Setters

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

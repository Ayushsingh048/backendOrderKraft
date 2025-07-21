
package com.entity;
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

    @Column(name = "unit_price")
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

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

    public void setQuantity(long l) {
        this.quantity = l;
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

package com.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;


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
    
    @Column(name = "order_name")
    private String orderName;
    
    @Column(name = "delivery_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate deliveryDate;  
    
    @Column(name = "supplier_id")
    private long supplierId;
    
    @Column(name = "product_id")
    private Long productId;
    

    // ✅ Add mapping to OrderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments;



	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

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

    public User getProcurementOfficer() {
        return procurementOfficer;
    }

    public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setProcurementOfficer(User user) {
        this.procurementOfficer = user;
    }
	
	public Long getProductId() { return productId; }
	
	public void setProductId(Long productId) { this.productId = productId; }
	
	 public List<OrderItem> getOrderItems() { return orderItems; }
	    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}

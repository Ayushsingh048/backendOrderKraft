package com.entity;
import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "orders")
public class Order {
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_seq")
	    @SequenceGenerator(name = "Order_seq", sequenceName = "Order_seq", allocationSize = 1)
    private long orderId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_amount")
    private long totalAmount;

    @ManyToOne
    @JoinColumn(name = "procurement_officer_id")
    private User procurementOfficer;

    public Order() {
    }

    //  Getters and Setters

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long string) {
        this.orderId = string;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

//    public String getFormattedOrderDate() 
//   {
//        if (orderDate == null) return null;
//        LocalDate sdf = new LocalDate("yyyy-MM-dd HH:mm:ss");
//        return sdf.format(orderDate);
//     }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getProcurementOfficer() {
        return procurementOfficer;
    }

    public void setProcurementOfficer(User user) {
        this.procurementOfficer = user;
    }

	public Object getName() {
		// TODO Auto-generated method stub
		return null;
	}
}

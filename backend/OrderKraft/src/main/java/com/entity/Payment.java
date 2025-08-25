
package com.entity;
import jakarta.persistence.*;


import java.time.LocalDate;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "payment")
public class Payment {

	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
	    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    private long paymentId;

    @Column(name = "amount")
    private long amount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private String status;
    
    @Column(name="session_id",length = 2000)
    private String session_id;

    public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	@OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public Payment() {
    }
    
    // ✅ Getters and Setters

    public long getPaymentId() {
        return paymentId;
    }


    public Payment(long paymentId, long amount, LocalDate paymentDate, String method, String status, Order order,String sessionid) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.method = method;
		this.status = status;
		this.order = order;
		
		this.session_id=sessionid;
	}

	public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

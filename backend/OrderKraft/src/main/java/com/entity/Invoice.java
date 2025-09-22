package com.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
public class Invoice {
  
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
	    @SequenceGenerator(name = "invoice_seq", sequenceName = "invoice_seq", allocationSize = 1)
    private long invoiceId;

   
    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public Invoice() {
    }

    // ✅ Getters and Setters

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

package com.entity;
import jakarta.persistence.*;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "invoice")
public class Invoice {
  
	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
	    @SequenceGenerator(name = "invoice_seq", sequenceName = "invoice_seq", allocationSize = 1)
    private String invoiceId;

   
    @Column(name = "invoice_date")
    private SimpleDateFormat invoiceDate;

    @Column(name = "due_date")
    private SimpleDateFormat dueDate;

    @Column(name = "total_amount")
    private long totalAmount;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public Invoice() {
    }

    // ✅ Getters and Setters

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public SimpleDateFormat getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(SimpleDateFormat invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public SimpleDateFormat getDueDate() {
        return dueDate;
    }

    public void setDueDate(SimpleDateFormat dueDate) {
        this.dueDate = dueDate;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

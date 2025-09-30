package com.entity;

import com.enums.ReturnReason;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "return_requests")
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "return_req_seq")
    @SequenceGenerator(name = "return_req_seq", sequenceName = "return_req_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long supplierId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ReturnReason reason;

    @Column(name = "comment_text", length = 500)
    private String comment;  // maps to column comment_text

    @Column(name = "request_date", nullable = false)
    @org.hibernate.annotations.ColumnDefault("SYSDATE")
    private LocalDate requestDate = LocalDate.now();

    @Column(nullable = false, length = 20)
    private String status; // PENDING / ACCEPTED / REJECTED
    
    @Column(name = "product_id")
    private Long productId;


    @Column(name = "quantity")
    private Integer quantity;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public ReturnReason getReason() { return reason; }
    public void setReason(ReturnReason reason) { this.reason = reason; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }


    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}

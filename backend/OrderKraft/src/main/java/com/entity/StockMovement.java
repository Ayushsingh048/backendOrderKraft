package com.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stock_movement")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movement_seq")
    @SequenceGenerator(name = "movement_seq", sequenceName = "movement_seq", allocationSize = 1)
    @Column(name = "movement_id")
    private Long movementId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "movement_date", nullable = false)  // renamed from "date"
    private LocalDate movementDate;

    @Column(name = "in_qty", nullable = false)
    private Integer inQty;

    @Column(name = "out_qty", nullable = false)
    private Integer outQty;

    @Column(name = "balance_qty", nullable = false)   // renamed from "balance"
    private Integer balanceQty;

    // getters and setters
    public Long getMovementId() {
        return movementId;
    }
    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public LocalDate getMovementDate() {
        return movementDate;
    }
    public void setMovementDate(LocalDate movementDate) {
        this.movementDate = movementDate;
    }
    public Integer getInQty() {
        return inQty;
    }
    public void setInQty(Integer inQty) {
        this.inQty = inQty;
    }
    public Integer getOutQty() {
        return outQty;
    }
    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }
    public Integer getBalanceQty() {
        return balanceQty;
    }
    public void setBalanceQty(Integer balanceQty) {
        this.balanceQty = balanceQty;
    }
}

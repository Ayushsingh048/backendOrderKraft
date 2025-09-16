package com.entity;

import java.time.LocalDate;
//import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name = "Inventory")
public class Inventory {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq")
    @SequenceGenerator(name = "inventory_seq", sequenceName = "inventory_seq", allocationSize = 1)
    private Long inventory_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;


    @ManyToOne
    @JoinColumn(name = "inventory_manager_id")
    private User inventoryManager;
    
    private String item_type;
    
    

    // Getters and Setters
    

    public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	public int getQuantity() { return quantity; }
    public Long getInventory_id() {
		return inventory_id;
	}
	public void setInventory_id(Long inventory_id) {
		this.inventory_id = inventory_id;
	}
	public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public User getInventoryManager() { return inventoryManager; }
    public void setInventoryManager(User inventoryManager) { this.inventoryManager = inventoryManager; }
}




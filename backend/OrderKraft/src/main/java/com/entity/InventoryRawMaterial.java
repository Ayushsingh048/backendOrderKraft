package com.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_rawmaterial")
public class InventoryRawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_rawmaterial_seq")
    @SequenceGenerator(name = "inventory_rawmaterial_seq", sequenceName = "inventory_rawmaterial_seq", allocationSize = 1)
    private Long inventory_rawmaterial_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "quantity")
    private int quantity;

    // Getters & Setters
    public Long getInventory_rawmaterial_id() { return inventory_rawmaterial_id; }
    public void setInventory_rawmaterial_id(Long inventory_rawmaterial_id) { this.inventory_rawmaterial_id = inventory_rawmaterial_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

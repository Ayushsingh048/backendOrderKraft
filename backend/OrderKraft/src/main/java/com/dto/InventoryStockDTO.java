package com.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class InventoryStockDTO {

    private long inventoryRawmaterialId; // use camelCase for DTO clients
    private String name;
    private Long categoryId;
    private String categoryName;
    private long quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate lastUpdated;

    public InventoryStockDTO() {}

    public InventoryStockDTO(long inventoryRawmaterialId, String name, Long categoryId, String categoryName, long quantity, LocalDate lastUpdated) {
        this.inventoryRawmaterialId = inventoryRawmaterialId;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    public long getInventoryRawmaterialId() {
        return inventoryRawmaterialId;
    }

    public void setInventoryRawmaterialId(long inventoryRawmaterialId) {
        this.inventoryRawmaterialId = inventoryRawmaterialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

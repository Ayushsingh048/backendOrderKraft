package com.dto;

public class ProductDTO {

    private Long product_id;         // Added to align with entity
    private String name;
    private String description;
    private Double unit_price;       // Changed to Double (same as entity)

    private Long category_id;        // For @ManyToOne relationship (Category)
    private Long bom_id;             // For @OneToOne relationship (BOM)

    // Constructors
    public ProductDTO() {
        super();
    }

    public ProductDTO(Long product_id, String name, String description, Double unit_price, Long category_id, Long bom_id) {
        super();
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
        this.category_id = category_id;
        this.bom_id = bom_id;
    }

    // Getters and Setters
    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getBom_id() {
        return bom_id;
    }

    public void setBom_id(Long bom_id) {
        this.bom_id = bom_id;
    }
}

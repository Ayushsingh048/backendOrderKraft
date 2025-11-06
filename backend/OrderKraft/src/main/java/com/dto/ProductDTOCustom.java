package com.dto;

public class ProductDTOCustom {

    private Long productId;
    private String name;
    private String description;
    private Double unitPrice;
    private Long categoryId;
    private String categoryName;

    private BOMDTO bom; // nested BOM

    public ProductDTOCustom() {}

    public ProductDTOCustom(Long productId, String name, String description, Double unitPrice,
                      Long categoryId, String categoryName, BOMDTO bom) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.bom = bom;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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

    public BOMDTO getBom() {
        return bom;
    }
    public void setBom(BOMDTO bom) {
        this.bom = bom;
    }
}

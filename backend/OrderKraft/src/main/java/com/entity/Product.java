package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
    @SequenceGenerator(name = "prod_seq", sequenceName = "prod_seq", allocationSize = 1)
    private Long product_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_price")
    //private double unit_price ;
    
    private Double unit_price; // Wrapper type allows NULL

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
   @OneToOne
   @JoinColumn(name="bom_id")
    private BOM bom;
   
   public Product()
   {
	   super();
   }

	public Product(Long product_id, String name, String description, double unit_price, Category category, BOM bom) {
	super();
	this.product_id = product_id;
	this.name = name;
	this.description = description;
	this.unit_price = unit_price;
	this.category = category;
	this.bom = bom;
}

	public Product() {
		super();
	}

	public BOM getBom() {
	return bom;
}

public void setBom(BOM bom) {
	this.bom = bom;
}

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

	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
    
    // Getters and Setters
    
}






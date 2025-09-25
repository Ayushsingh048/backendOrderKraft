package com.dto;

public class ProductDTO {
	private String name;
	private String description;
	private float unit_price;
	private long category_id;
	private long production_manager_id;
	
	//all-args-constr
	public ProductDTO(String name, String description, float unit_price, long category_id,
			long production_manager_id) {
		super();
		this.name = name;
		this.description = description;
		this.unit_price = unit_price;
		this.category_id = category_id;
		this.production_manager_id = production_manager_id;
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
	public float getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(float unit_price) {
		this.unit_price = unit_price;
	}
	public long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}
	public long getProduction_manager_id() {
		return production_manager_id;
	}
	public void setProduction_manager_id(long production_manager_id) {
		this.production_manager_id = production_manager_id;
	}
	
	//no-args constr
	public ProductDTO() {}
	
	public ProductDTO(String name, String description, float unit_price,
			long production_manager_id) {
		super();
		this.name = name;
		this.description = description;
		this.unit_price = unit_price;
		this.production_manager_id = production_manager_id;
	}
	
}

package com.dto;

import java.util.Set;

public class Raw_MaterialDTO {
//vars
	private long id;
	private String name;
	private double unit_cost;
	private long stock_quantity;
	private String status;
	
	private Set<Long> supplierIds;

	public Set<Long> getSupplierIds() {
	    return supplierIds;
	}

	public void setSupplierIds(Set<Long> supplierIds) {
	    this.supplierIds = supplierIds;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getUnit_cost() {
		return unit_cost;
	}
	public void setUnit_cost(float unit_cost) {
		this.unit_cost = unit_cost;
	}
	public long getStock_quantity() {
		return stock_quantity;
	}
	public void setStock_quantity(long stock_quantity) {
		this.stock_quantity = stock_quantity;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Raw_MaterialDTO(long id, String name, float unit_cost, long stock_quantity, String status) {
		super();
		this.id = id;
		this.name = name;
		this.unit_cost = unit_cost;
		this.stock_quantity = stock_quantity;
		this.status = status;
	}
	public Raw_MaterialDTO() {
		
	}
	
	
}

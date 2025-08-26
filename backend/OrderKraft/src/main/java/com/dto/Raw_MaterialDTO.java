package com.dto;

import java.util.Set;

public class Raw_MaterialDTO {
//vars
	private long id;
	private String name;
	private Double unitCost;
	private Long stockQuantity;
	private String status;
	
	 //  Many suppliers, so we keep a set of supplier IDs
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
	

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Long getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Raw_MaterialDTO(long id, String name, double unit_cost, long stock_quantity, String status) {
		super();
		this.id = id;
		this.name = name;
		this.unitCost = unit_cost;
		this.stockQuantity= stock_quantity;
		this.status = status;
	}
	public Raw_MaterialDTO() {
		
	}
	
	
}

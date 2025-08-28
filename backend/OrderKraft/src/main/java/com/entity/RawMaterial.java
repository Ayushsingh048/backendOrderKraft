package com.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;

@Entity
public class RawMaterial {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long raw_material_id;
	
	private String name;
	private Double unitCost;
	private Long stockQuantity;
	private String status;
	
	
	/**
     * Many-to-Many relationship:
     * - One RawMaterial can be supplied by multiple suppliers
     * - One Supplier can supply multiple raw materials
     * 
     * JPA will create a join table `supplier_raw_material`
     * with columns `raw_material_id` and `supplier_id`.
     */
    @ManyToMany
    @JoinTable(
        name = "supplier_raw_material",
        joinColumns = @JoinColumn(name = "raw_material_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();

	public RawMaterial(Long raw_material_id, String name, Double unit_cost, long stock_quantity, String status,
			Set<Supplier> suppliers) {
		super();
		this.raw_material_id = raw_material_id;
		this.name = name;
		this.unitCost = unit_cost;
		this.stockQuantity = stock_quantity;
		this.status = status;
		this.suppliers = suppliers;
	}

	public RawMaterial() {
		// TODO Auto-generated constructor stub
	}

	public Long getRaw_material_id() {
		return raw_material_id;
	}

	public void setRaw_material_id(Long raw_material_id) {
		this.raw_material_id = raw_material_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Set<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Set<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	
	
	
}

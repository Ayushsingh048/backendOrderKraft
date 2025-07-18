package com.entity;

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
	private Double unit_cost;
	private long stock_quantity;
	private String status;
	
	@ManyToMany
	@JoinTable(
	    name = "supplier_raw_material",
	    joinColumns      = @JoinColumn(name = "raw_material_id"),
	    inverseJoinColumns = @JoinColumn(name = "supplier_id")
	)
	private Set<Supplier> suppliers;

	public RawMaterial(Long raw_material_id, String name, Double unit_cost, int stock_quantity, String status,
			Set<Supplier> suppliers) {
		super();
		this.raw_material_id = raw_material_id;
		this.name = name;
		this.unit_cost = unit_cost;
		this.stock_quantity = stock_quantity;
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

	public Double getUnit_cost() {
		return unit_cost;
	}

	public void setUnit_cost(Double unit_cost) {
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

	public Set<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Set<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	
	
	
}

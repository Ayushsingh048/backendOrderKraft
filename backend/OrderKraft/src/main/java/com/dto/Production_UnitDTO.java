package com.dto;



public class Production_UnitDTO {
//vars
 private long unit_id;
 private String name;
 private long capacity;
 private long production_manager_id;
public long getUnit_id() {
	return unit_id;
}
public void setUnit_id(long unit_id) {
	this.unit_id = unit_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public long getCapacity() {
	return capacity;
}
public void setCapacity(long capacity) {
	this.capacity = capacity;
}


public long getProduction_manager_id() {
	return production_manager_id;
}
public void setProduction_manager_id(long production_manager_id) {
	this.production_manager_id = production_manager_id;
}

public Production_UnitDTO(long unit_id, String name, long capacity, long production_manager_id) {
	super();
	this.unit_id = unit_id;
	this.name = name;
	this.capacity = capacity;
	this.production_manager_id = production_manager_id;
}
public Production_UnitDTO() {
	
}
 
}

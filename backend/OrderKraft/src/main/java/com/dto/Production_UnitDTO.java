package com.dto;



public class Production_UnitDTO {
//vars
 private long unit_id;
 private String name;
 private long capacity;
 private long manager_id;
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
public long getManager_id() {
	return manager_id;
}
public void setManager_id(long manager_id) {
	this.manager_id = manager_id;
}
public Production_UnitDTO(long unit_id, String name, long capacity, long manager_id) {
	super();
	this.unit_id = unit_id;
	this.name = name;
	this.capacity = capacity;
	this.manager_id = manager_id;
}
public Production_UnitDTO() {
	
}
 
}

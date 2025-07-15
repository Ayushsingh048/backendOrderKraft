package com.dto;

public class CategoryDTO {
	//vars
private long category_id;
private String name;
private String description;
private long inventory_manager_id;
public long getCategory_id() {
	return category_id;
}
public void setCategory_id(long category_id) {
	this.category_id = category_id;
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
public long getInventory_manager_id() {
	return inventory_manager_id;
}
public void setInventory_manager_id(long inventory_manager_id) {
	this.inventory_manager_id = inventory_manager_id;
}
public CategoryDTO(long category_id, String name, String description, long inventory_manager_id) {
	super();
	this.category_id = category_id;
	this.name = name;
	this.description = description;
	this.inventory_manager_id = inventory_manager_id;
}
public CategoryDTO() {}


}

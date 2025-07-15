package com.dto;



public class InventoryDTO {

	//vars
private long inventory_id;
private long product_id;
private long quantity;
private String last_updated;
private String inventory_manager_id;
public InventoryDTO(long inventory_id, long product_id, long quantity, String last_updated,
		String inventory_manager_id) {
	super();
	this.inventory_id = inventory_id;
	this.product_id = product_id;
	this.quantity = quantity;
	this.last_updated = last_updated;
	this.inventory_manager_id = inventory_manager_id;
}
public long getInventory_id() {
	return inventory_id;
}
public void setInventory_id(long inventory_id) {
	this.inventory_id = inventory_id;
}
public long getProduct_id() {
	return product_id;
}
public void setProduct_id(long product_id) {
	this.product_id = product_id;
}
public long getQuantity() {
	return quantity;
}
public void setQuantity(long quantity) {
	this.quantity = quantity;
}
public String getLast_updated() {
	return last_updated;
}
public void setLast_updated(String last_updated) {
	this.last_updated = last_updated;
}
public String getInventory_manager_id() {
	return inventory_manager_id;
}
public void setInventory_manager_id(String inventory_manager_id) {
	this.inventory_manager_id = inventory_manager_id;
}
public InventoryDTO() {}


}

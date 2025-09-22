package com.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InventoryDTO {

	//vars
private long inventory_id;
private long product_id;
private long quantity;
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate last_updated;
private long inventory_manager_id;

private String item_type;
//all-args-constr

public long getInventory_id() {
	return inventory_id;
}
public InventoryDTO(long inventory_id, long product_id, long quantity, LocalDate last_updated,
		long inventory_manager_id,String item_type) {
	super();
	this.inventory_id = inventory_id;
	this.product_id = product_id;
	this.quantity = quantity;
	this.last_updated = last_updated;
	this.inventory_manager_id = inventory_manager_id;
	this.item_type=item_type;
}
public void setInventory_id(long inventory_id) {
	this.inventory_id = inventory_id;
}
public String getItem_type() {
	return item_type;
}
public void setItem_type(String item_type) {
	this.item_type = item_type;
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


public LocalDate getLast_updated() {
	return last_updated;
}
public void setLast_updated(LocalDate last_updated) {
	this.last_updated = last_updated;
}
public long getInventory_manager_id() {
	return inventory_manager_id;
}
public void setInventory_manager_id(long inventory_manager_id) {
	this.inventory_manager_id = inventory_manager_id;
}
//no-args constr
public InventoryDTO() {}


}

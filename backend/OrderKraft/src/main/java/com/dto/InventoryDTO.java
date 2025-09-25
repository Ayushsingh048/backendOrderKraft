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

private String item_type;
//all-args-constr

public long getInventory_id() {
	return inventory_id;
}
public InventoryDTO(long inventory_id, long product_id, long quantity, LocalDate last_updated,
		String item_type) {
	super();
	this.inventory_id = inventory_id;
	this.product_id = product_id;
	this.quantity = quantity;
	this.last_updated = last_updated;
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

//no-args constr
public InventoryDTO() {}


}

package com.dto;
import java.util.Date;

public class Inventory_AlertDTO {

	
//vars
	
private long alert_id;
private long product_id;
private String  alert_type;
private Date trigger_date;
private long inventory_manager_id;

//getters and setters
public long getAlert_id() {
	return alert_id;
}
public void setAlert_id(long alert_id) {
	this.alert_id = alert_id;
}
public long getProduct_id() {
	return product_id;
}
public void setProduct_id(long product_id) {
	this.product_id = product_id;
}
public String getAlert_type() {
	return alert_type;
}
public void setAlert_type(String alert_type) {
	this.alert_type = alert_type;
}
public Date getTrigger_date() {
	return trigger_date;
}
public void setTrigger_date(Date trigger_date) {
	this.trigger_date = trigger_date;
}
public long getInventory_manager_id() {
	return inventory_manager_id;
}
public void setInventory_manager_id(long inventory_manager_id) {
	this.inventory_manager_id = inventory_manager_id;
}


//no-args constr
public Inventory_AlertDTO() {}


//all-args-constr
public Inventory_AlertDTO(long alert_id, long product_id, String alert_type, Date trigger_date,
		long inventory_manager_id) {
	super();
	this.alert_id = alert_id;
	this.product_id = product_id;
	this.alert_type = alert_type;
	this.trigger_date = trigger_date;
	this.inventory_manager_id = inventory_manager_id;
}

}

package com.dto;

import java.time.LocalDate;


public class OrderDTO {
private long order_id;
private LocalDate  order_date;//needs to be formatted, if necessary!
private String status;


public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

private long total_amount;
private long procurement_officer_id;

//getters and setters
public long getOrder_id() {
	return order_id;
}
public void setOrder_id(long order_id) {
	this.order_id = order_id;
}
public LocalDate  getOrder_date() {
	return order_date;
}
public void setOrder_date(LocalDate  order_date) {
	this.order_date = order_date;
}

public long getTotal_amount() {
	return total_amount;
}
public void setTotal_amount(long total_amount) {
	this.total_amount = total_amount;
}
public long getProcurement_officer_id() {
	return procurement_officer_id;
}
public void setProcurement_officer_id(long procurement_officer_id) {
	this.procurement_officer_id = procurement_officer_id;
}

//no-args constr.
public OrderDTO() {}

//all-args constr.
public OrderDTO(long order_id, LocalDate order_date, String status, long total_amount, long procurement_officer_id) {
	super();
	this.order_id = order_id;
	this.order_date = order_date;
	this.status = status;
	this.total_amount = total_amount;
	this.procurement_officer_id = procurement_officer_id;
}
}
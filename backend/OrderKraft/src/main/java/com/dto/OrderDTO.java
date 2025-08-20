package com.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;


public class OrderDTO {

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
private LocalDate  order_date;//needs to be formatted, if necessary!
private String status;
private long total_amount;
private long procurement_officer_id;

public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
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
public OrderDTO(LocalDate order_date, String status, long total_amount, long procurement_officer_id) {
	super();
	this.order_date = order_date;
	this.status = status;
	this.total_amount = total_amount;
	this.procurement_officer_id = procurement_officer_id;
}
}
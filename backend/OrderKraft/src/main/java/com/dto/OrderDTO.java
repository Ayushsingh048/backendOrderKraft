package com.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;


public class OrderDTO {

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
private LocalDate  order_date;//needs to be formatted, if necessary!
private String status;
private BigDecimal total_amount;
private long procurement_officer_id;
private String order_name;

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

public BigDecimal getTotal_amount() {
	return total_amount;
}
public void setTotal_amount(BigDecimal total_amount) {
	this.total_amount = total_amount;
}
public long getProcurement_officer_id() {
	return procurement_officer_id;
}
public void setProcurement_officer_id(long procurement_officer_id) {
	this.procurement_officer_id = procurement_officer_id;
}

public String getOrder_name() {
	return order_name;
}
public void setOrder_name(String order_name) {
	this.order_name = order_name;
}
//no-args constr.
public OrderDTO() {}

//all-args constr.
public OrderDTO(LocalDate order_date, String status, BigDecimal total_amount, long procurement_officer_id,
		String order_name) {
	super();
	this.order_date = order_date;
	this.status = status;
	this.total_amount = total_amount;
	this.procurement_officer_id = procurement_officer_id;
	this.order_name = order_name;
}


}
package com.dto;

import java.util.Date;


public class InvoiceDTO {
	//vars
private long invoice_id;
private Date invoice_date;
private Date due_date;
private float total_amount;
public InvoiceDTO(long invoice_id, Date invoice_date, Date due_date, float total_amount) {
	super();
	this.invoice_id = invoice_id;
	this.invoice_date = invoice_date;
	this.due_date = due_date;
	this.total_amount = total_amount;
}

public InvoiceDTO() {}

public long getInvoice_id() {
	return invoice_id;
}

public void setInvoice_id(long invoice_id) {
	this.invoice_id = invoice_id;
}

public Date getInvoice_date() {
	return invoice_date;
}

public void setInvoice_date(Date invoice_date) {
	this.invoice_date = invoice_date;
}

public Date getDue_date() {
	return due_date;
}

public void setDue_date(Date due_date) {
	this.due_date = due_date;
}

public float getTotal_amount() {
	return total_amount;
}

public void setTotal_amount(float total_amount) {
	this.total_amount = total_amount;
}




}

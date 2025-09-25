package com.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class InvoiceDTO {
	//vars
private long invoice_id;
private long orderid;
private LocalDate invoice_date;
private LocalDate due_date;
private BigDecimal total_amount;

private List<Order_ItemDTO> orderItems; // ✅ Include order items
//public InvoiceDTO(long invoice_id, Date invoice_date, Date due_date, BigDecimal total_amount) {
//	super();
//	this.invoice_id = invoice_id;
//	this.invoice_date = invoice_date;
//	this.due_date = due_date;
//	this.total_amount = total_amount;
//}





public long getOrderid() {
	return orderid;
}


public List<Order_ItemDTO> getOrderItems() {
	return orderItems;
}


public void setOrderItems(List<Order_ItemDTO> orderItems) {
	this.orderItems = orderItems;
}


public InvoiceDTO(long invoice_id, long orderid, LocalDate invoice_date, LocalDate due_date, BigDecimal total_amount,
		List<Order_ItemDTO> orderItems) {
	super();
	this.invoice_id = invoice_id;
	this.orderid = orderid;
	this.invoice_date = invoice_date;
	this.due_date = due_date;
	this.total_amount = total_amount;
	this.orderItems = orderItems;
}


public void setOrderid(long orderid) {
	this.orderid = orderid;
}


public InvoiceDTO() {}

public long getInvoice_id() {
	return invoice_id;
}

public void setInvoice_id(long invoice_id) {
	this.invoice_id = invoice_id;
}

public LocalDate getInvoice_date() {
	return invoice_date;
}

public void setInvoice_date(LocalDate invoice_date) {
	this.invoice_date = invoice_date;
}

public LocalDate getDue_date() {
	return due_date;
}

public void setDue_date(LocalDate due_date) {
	this.due_date = due_date;
}

public BigDecimal getTotal_amount() {
	return total_amount;
}

public void setTotal_amount(BigDecimal total_amount) {
	this.total_amount = total_amount;
}




}

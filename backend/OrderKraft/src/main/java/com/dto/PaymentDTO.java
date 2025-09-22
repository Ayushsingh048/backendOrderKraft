package com.dto;
import java.time.LocalDateTime;
public class PaymentDTO {
	//vars
private long payment_id;
private long amount;
private LocalDateTime payment_date;
private String method;
private String status;
private long order_id;
private String session_id;


public String getSession_id() {
	return session_id;
}
public void setSession_id(String session_id) {
	this.session_id = session_id;
}
public long getPayment_id() {
	return payment_id;
}
public void setPayment_id(long payment_id) {
	this.payment_id = payment_id;
}
public long getAmount() {
	return amount;
}
public void setAmount(long amount) {
	this.amount = amount;
}
public LocalDateTime getPayment_date() {
	return payment_date;
}
public void setPayment_date(LocalDateTime payment_date) {
	this.payment_date = payment_date;
}
public String getMethod() {
	return method;
}
public void setMethod(String method) {
	this.method = method;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public long getOrder_id() {
	return order_id;
}
public void setOrder_id(long order_id) {
	this.order_id = order_id;
}
public PaymentDTO(long payment_id, long amount, LocalDateTime payment_date, String method, String status, long order_id,String session_id) {
	super();
	this.payment_id = payment_id;
	this.amount = amount;
	this.payment_date = payment_date;
	this.method = method;
	this.status = status;
	this.order_id = order_id;
	this.session_id=session_id;
}
public PaymentDTO() {}




}

package com.dto;

import java.util.Date;


public class ContractDTO {
	//vars
private long contract_id;
private long supplier_id;
private Date start_date;
private Date end_date;
private String terms;
public ContractDTO(long contract_id, long supplier_id, Date start_date, Date end_date, String terms) {
	super();
	this.contract_id = contract_id;
	this.supplier_id = supplier_id;
	this.start_date = start_date;
	this.end_date = end_date;
	this.terms = terms;
}
public long getContract_id() {
	return contract_id;
}
public void setContract_id(long contract_id) {
	this.contract_id = contract_id;
}
public long getSupplier_id() {
	return supplier_id;
}
public void setSupplier_id(long supplier_id) {
	this.supplier_id = supplier_id;
}
public Date getStart_date() {
	return start_date;
}
public void setStart_date(Date start_date) {
	this.start_date = start_date;
}
public Date getEnd_date() {
	return end_date;
}
public void setEnd_date(Date end_date) {
	this.end_date = end_date;
}
public String getTerms() {
	return terms;
}
public void setTerms(String terms) {
	this.terms = terms;
}
public ContractDTO() {}
	



}

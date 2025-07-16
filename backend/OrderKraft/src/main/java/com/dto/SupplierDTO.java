package com.dto;

public class SupplierDTO {
	//vars
private long supplier_id;
private String name;
private String contact_person;
private String email;
private String phone;
private float rating;
public long getSupplier_id() {
	return supplier_id;
}
public void setSupplier_id(long supplier_id) {
	this.supplier_id = supplier_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getContact_person() {
	return contact_person;
}
public void setContact_person(String contact_person) {
	this.contact_person = contact_person;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public float getRating() {
	return rating;
}
public void setRating(float rating) {
	this.rating = rating;
}
public SupplierDTO(long supplier_id, String name, String contact_person, String email, String phone, float rating) {
	super();
	this.supplier_id = supplier_id;
	this.name = name;
	this.contact_person = contact_person;
	this.email = email;
	this.phone = phone;
	this.rating = rating;
}
public SupplierDTO() {
	
}


}

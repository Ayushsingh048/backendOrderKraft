package com.dto;

import java.util.Set;

public class SupplierDTO {
	//vars
private Long supplierId;
private String name;
private String contact_person;
private String email;
private String phone;
private float rating;
private Set<Long> rawMaterialIds;
private  String accNum;

public String getAccNum() {
	return accNum;
}

public void setAccNum(String accNum) {
	this.accNum = accNum;
}

public Set<Long> getRawMaterialIds() {
    return rawMaterialIds;
}

public void setRawMaterialIds(Set<Long> rawMaterialIds) {
    this.rawMaterialIds = rawMaterialIds;
}


public Long getSupplierId() {
	return supplierId;
}

public void setSupplierId(Long supplierId) {
	this.supplierId = supplierId;
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
public SupplierDTO(long supplier_id, String name, String contact_person, String email, String phone, float rating, String acc) {
	super();
	this.supplierId = supplier_id;
	this.name = name;
	this.contact_person = contact_person;
	this.email = email;
	this.phone = phone;
	this.rating = rating;
	this.accNum=acc;
}
public SupplierDTO() {
	
}


}

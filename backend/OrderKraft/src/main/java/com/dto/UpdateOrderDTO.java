package com.dto;

public class UpdateOrderDTO {
   int ID;
   Long Quantity;
   String Name;
   
public UpdateOrderDTO() {
	super();
}
public UpdateOrderDTO(int iD, Long quantity, String name) {
	super();
	ID = iD;
	Quantity = quantity;
	Name = name;
}
public int getID() {
	return ID;
}
public void setID(int iD) {
	ID = iD;
}
public Long getQuantity() {
	return Quantity;
}
public void setQuantity(Long quantity) {
	Quantity = quantity;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
   
}

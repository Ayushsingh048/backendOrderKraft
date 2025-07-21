package com.dto;



public class Order_ItemDTO {

	private long quantity;
	private float unit_price;
	private long order_id;
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public float getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(float unit_price) {
		this.unit_price = unit_price;
	}
	public long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}
	public Order_ItemDTO(long quantity, float unit_price, long order_id) {
		super();
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.order_id = order_id;
	}
	public Order_ItemDTO() {}

	
}

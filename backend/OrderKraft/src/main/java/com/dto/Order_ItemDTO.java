package com.dto;

import java.math.BigDecimal;

public class Order_ItemDTO {

	private long quantity;
	private BigDecimal  unit_price;
	private long order_id;
	private String name;
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}
	public long getOrder_id() {
		return order_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Order_ItemDTO(long quantity, BigDecimal unit_price, long order_id, String name) {
		super();
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.order_id = order_id;
		this.name = name;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public Order_ItemDTO() {}

	
}

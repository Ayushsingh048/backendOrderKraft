package com.dto;

import java.time.LocalDateTime;

public class InventoryAlertDTO {
	
	private String inventoryName;
	private String inventoryRawName;
	private String alertType;
	private LocalDateTime date;
	private Integer inventoryQuantity;
	private Integer inventoryrawQuantity;
	
	public InventoryAlertDTO(String inventoryName, String inventoryRawName, String alertType, LocalDateTime date,
			Integer invQuantity, Integer rawQuantity) {
		super();
		this.inventoryName = inventoryName;
		this.inventoryRawName = inventoryRawName;
		this.alertType = alertType;
		this.date = date;
		this.inventoryQuantity = invQuantity;
		this.inventoryrawQuantity = rawQuantity;
	}
	public String getInventoryName() {
		return inventoryName;
	}
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}
	public String getInventoryRawName() {
		return inventoryRawName;
	}
	public void setInventoryRawName(String inventoryRawName) {
		this.inventoryRawName = inventoryRawName;
	}
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public Integer getInventoryrawQuantity() {
		return inventoryrawQuantity;
	}
	public void setInventoryrawQuantity(Integer inventoryrawQuantity) {
		this.inventoryrawQuantity = inventoryrawQuantity;
	}
	
	
	
}

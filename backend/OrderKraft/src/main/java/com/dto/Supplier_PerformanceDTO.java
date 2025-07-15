package com.dto;

public class Supplier_PerformanceDTO {
	//vars
private long performance_id;
private long supplier_id;
private float delivery_score;
private float quality_score;
private float communication_score;
public long getPerformance_id() {
	return performance_id;
}
public void setPerformance_id(long performance_id) {
	this.performance_id = performance_id;
}
public long getSupplier_id() {
	return supplier_id;
}
public void setSupplier_id(long supplier_id) {
	this.supplier_id = supplier_id;
}
public float getDelivery_score() {
	return delivery_score;
}
public void setDelivery_score(float delivery_score) {
	this.delivery_score = delivery_score;
}
public float getQuality_score() {
	return quality_score;
}
public void setQuality_score(float quality_score) {
	this.quality_score = quality_score;
}
public float getCommunication_score() {
	return communication_score;
}
public void setCommunication_score(float communication_score) {
	this.communication_score = communication_score;
}
public Supplier_PerformanceDTO(long performance_id, long supplier_id, float delivery_score, float quality_score,
		float communication_score) {
	super();
	this.performance_id = performance_id;
	this.supplier_id = supplier_id;
	this.delivery_score = delivery_score;
	this.quality_score = quality_score;
	this.communication_score = communication_score;
}
public Supplier_PerformanceDTO() {
	
}


}


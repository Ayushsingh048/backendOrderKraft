package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class SupplierPerformance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long performance_id;
	
	private Double delivery_score;
	private Double quality_score;
	private Double communication_score;
	
	@OneToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	public SupplierPerformance() {}
	
	public SupplierPerformance(Long performance_id, Double delivery_score, Double quality_score,
			Double communication_score, Supplier supplier_id) {
		super();
		this.performance_id = performance_id;
		this.delivery_score = delivery_score;
		this.quality_score = quality_score;
		this.communication_score = communication_score;
		this.supplier = supplier_id;
	}

	public Long getPerformance_id() {
		return performance_id;
	}

	public void setPerformance_id(Long performance_id) {
		this.performance_id = performance_id;
	}

	public Double getDelivery_score() {
		return delivery_score;
	}

	public void setDelivery_score(Double delivery_score) {
		this.delivery_score = delivery_score;
	}

	public Double getQuality_score() {
		return quality_score;
	}

	public void setQuality_score(Double quality_score) {
		this.quality_score = quality_score;
	}

	public Double getCommunication_score() {
		return communication_score;
	}

	public void setCommunication_score(Double communication_score) {
		this.communication_score = communication_score;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	
}

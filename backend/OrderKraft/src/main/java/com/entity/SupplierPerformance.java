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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sup_perf_seq")
	@SequenceGenerator(name = "sup_perf_seq", sequenceName = "sup_perf_seq", allocationSize = 1)
	private Long performance_id;
	
	private Double delivery_score;
	private Double quality_score;
	private Double communication_score;
	private Double average_score;
	
	public Double getAverage_score() {
		return average_score;
	}

	public void setAverage_score() {
		this.average_score = ((delivery_score+quality_score+communication_score)/3.0);
	}

	@OneToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	public SupplierPerformance() {
		delivery_score=0.0;
		communication_score=0.0;
		quality_score=0.0;
		average_score=((delivery_score+quality_score+communication_score)/3.0);
	}
	
	public SupplierPerformance(Long performance_id, Double delivery_score, Double quality_score,
			Double communication_score, Supplier supplier_id) {
		super();
		this.performance_id = performance_id;
		this.delivery_score = delivery_score;
		this.quality_score = quality_score;
		this.communication_score = communication_score;
		this.supplier = supplier_id;
		this.average_score=((delivery_score+quality_score+communication_score)/3);
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

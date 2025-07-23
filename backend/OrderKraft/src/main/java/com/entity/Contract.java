package com.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long contract_id;
	
	@ManyToOne
    @JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	private LocalDate start_date;
	private LocalDate end_date;
	private String terms;
	
	
	
	
	
	
	
	
	public Contract(Long contract_id, Supplier supplier, LocalDate start_date, LocalDate end_date,
			String terms) {
		super();
		this.contract_id = contract_id;
		this.supplier = supplier;
		this.start_date = start_date;
		this.end_date = end_date;
		this.terms = terms;
	}



	public Supplier getSupplier() {
		return supplier;
	}



	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}



	public Contract() {
		super();
	}



	public Long getContract_id() {
		return contract_id;
	}
	public void setContract_id(Long contract_id) {
		this.contract_id = contract_id;
	}
	
	
//	public Long getSupplier_id() {
//		return supplier_id;
//	}
//	public void setSupplier_id(Long supplier_id) {
//		this.supplier_id = supplier_id;
//	}
	
	
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public LocalDate getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	
}

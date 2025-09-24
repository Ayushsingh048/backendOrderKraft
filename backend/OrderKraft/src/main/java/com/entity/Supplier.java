package com.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_seq")
	@SequenceGenerator(name = "supplier_seq", sequenceName = "supplier_seq", allocationSize = 1)
	private Long supplierId;
	
	private String name;
	private String contact_person;
	private String email;
	private String phone;
	private float rating;
	private String accNum;
	
	
	@OneToOne
	@JoinColumn(name = "user_id" )
	private User user;
	
    public String getAccNum() {
		return accNum;
	}
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}

	/**
     * Many-to-Many (reverse side).
     * 
     * The `mappedBy = "suppliers"` means Supplier doesn’t own the join table;
     * RawMaterial owns it.
     */
    @ManyToMany(mappedBy = "suppliers")
    @JsonBackReference
    private Set<RawMaterial> rawMaterials = new HashSet<>();


	public Supplier(Long supplier_id, String name, String contact_person, String email, String phone, float rating,String acc) {
		super();
		this.supplierId = supplier_id;
		this.name = name;
		this.contact_person = contact_person;
		this.email = email;
		this.phone = phone;
		this.rating = rating;
		this.accNum=acc;
	}
	public Supplier() {
		super();
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

	public Set<RawMaterial> getRawMaterials() {
		return rawMaterials;
	}

	public void setRawMaterials(Set<RawMaterial> rawMaterials) {
		this.rawMaterials = rawMaterials;
	}
	

	
}

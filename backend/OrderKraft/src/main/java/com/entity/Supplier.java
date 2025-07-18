package com.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long supplier_id;
	
	private String name;
	private String contact_person;
	private String email;
	private String phone;
	private float rating;
	
	@ManyToMany(mappedBy = "suppliers")
	private Set<RawMaterial> raw_material;


	public Supplier(Long supplier_id, String name, String contact_person, String email, String phone, float rating) {
		super();
		this.supplier_id = supplier_id;
		this.name = name;
		this.contact_person = contact_person;
		this.email = email;
		this.phone = phone;
		this.rating = rating;
	}
	
	
	
	
	public Supplier() {
		super();
	}




	public Long getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
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
		return raw_material;
	}
	public void setRawMaterials(Set<RawMaterial> raw_material) {
		this.raw_material = raw_material;
	}
	
}

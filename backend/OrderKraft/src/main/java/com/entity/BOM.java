package com.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;


@Entity
public class BOM {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "bomseq")
	@SequenceGenerator(name="bomseq",sequenceName = "bomseq",allocationSize = 1)
	Long bom_id;
	String bomName;
	String remark;
	
//	@JsonBackReference
	@OneToMany(mappedBy = "bom",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<BOM_Material> materials;
	
	public List<BOM_Material> getMaterials() {
		return materials;
	}
	public void setMaterials(List<BOM_Material> materials) {
		this.materials = materials;
	}
	public BOM() {
		super();
	}
	public BOM(Long bom_id, String bom_name, String remark, List<BOM_Material> materials) {
		super();
		this.bom_id = bom_id;
		this.bomName = bom_name;
		this.remark = remark;
		this.materials = materials;
	}
	public Long getBom_id() {
		return bom_id;
	}
	public void setBom_id(Long bom_id) {
		this.bom_id = bom_id;
	}
	public String getbomName() {
		return bomName;
	}
	public void setbomName(String bom_name) {
		this.bomName = bom_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}

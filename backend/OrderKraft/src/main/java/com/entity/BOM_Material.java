package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class BOM_Material {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "materialseq")
	@SequenceGenerator(name="materialseq",sequenceName = "materialseq",allocationSize = 1)
	Long materialId;
	
	
	@ManyToOne
	@JoinColumn(name="bom_id")
	private BOM bom;
	
	@ManyToOne
	@JoinColumn(name="raw_material_id")
	private InventoryRawMaterial rawmaterial;
	
	private Long qntperunit;

	public BOM_Material(Long materialId, BOM bom, InventoryRawMaterial rawmaterial, Long qntperunit) {
		super();
		this.materialId = materialId;
		this.bom = bom;
		this.rawmaterial = rawmaterial;
		this.qntperunit = qntperunit;
	}

	public BOM_Material() {
		super();
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public BOM getBom() {
		return bom;
	}

	public void setBom(BOM bom) {
		this.bom = bom;
	}

	public InventoryRawMaterial getRawmaterial() {
		return rawmaterial;
	}

	public void setRawmaterial(InventoryRawMaterial rawmaterial) {
		this.rawmaterial = rawmaterial;
	}

	public Long getQntperunit() {
		return qntperunit;
	}

	public void setQntperunit(Long qntperunit) {
		this.qntperunit = qntperunit;
	}
	
}

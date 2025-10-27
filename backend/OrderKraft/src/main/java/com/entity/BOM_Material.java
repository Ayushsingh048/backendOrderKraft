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
	int materialId;
	
	
	@ManyToOne
	@JoinColumn(name="bom_id")
	private BOM bom;
	
	@ManyToOne
	@JoinColumn(name="raw_material_id")
	private InventoryRawMaterial rawmaterial;
	
	private int qntperunit;

	public BOM_Material(int materialId, BOM bom, InventoryRawMaterial rawmaterial, int qntperunit) {
		super();
		this.materialId = materialId;
		this.bom = bom;
		this.rawmaterial = rawmaterial;
		this.qntperunit = qntperunit;
	}

	public BOM_Material() {
		super();
	}

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
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

	public int getQntperunit() {
		return qntperunit;
	}

	public void setQntperunit(int qntperunit) {
		this.qntperunit = qntperunit;
	}
	
}

package com.dto;

public class BOMMaterialDTO {
    private Long materialId;
//    private String materialName;
    private String rawMaterialName;
   

	private Long bomId;
    private Long rawMaterialId;
    private Long qntPerUnit;
//    private InventoryRawMaterialService inventserv;

    public BOMMaterialDTO() {}

    public BOMMaterialDTO(Long materialId, Long bomId, Long rawMaterialId, String rawMaterialName, Long qntPerUnit) {
        this.materialId = materialId;
        this.bomId = bomId;
        this.rawMaterialId = rawMaterialId;
        this.rawMaterialName = rawMaterialName;
        this.qntPerUnit = qntPerUnit;
    }
    public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}
    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
        
    }

    public Long getQntPerUnit() {
        return qntPerUnit;
    }

    public void setQntPerUnit(Long qntPerUnit) {
        this.qntPerUnit = qntPerUnit;
    }
}

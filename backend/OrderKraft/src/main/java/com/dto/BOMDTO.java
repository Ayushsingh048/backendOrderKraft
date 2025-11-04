package com.dto;

import java.util.List;

public class BOMDTO {
    private Long bomId;
    private String bomName;
    private String remark;
    private List<BOMMaterialDTO> materials;

    public BOMDTO() {}

    public BOMDTO(Long bomId, String bomName, String remark, List<BOMMaterialDTO> materials) {
        this.bomId = bomId;
        this.bomName = bomName;
        this.remark = remark;
        this.materials = materials;
    }

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public String getBomName() {
        return bomName;
    }

    public void setBomName(String bomName) {
        this.bomName = bomName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BOMMaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<BOMMaterialDTO> materials) {
        this.materials = materials;
    }
}

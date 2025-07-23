package com.service;

import java.util.List;

import com.dto.Raw_MaterialDTO;
import com.entity.RawMaterial;

public interface RawMaterialService {
	public RawMaterial createRawMaterial(Raw_MaterialDTO dto);

	RawMaterial getRawMaterialById(Long id);

	List<RawMaterial> getAllRawMaterials();

}

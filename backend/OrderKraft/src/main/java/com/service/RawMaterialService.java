package com.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.dto.Raw_MaterialDTO;
import com.entity.RawMaterial;
import com.entity.Supplier;

public interface RawMaterialService {
	public RawMaterial createRawMaterial(Raw_MaterialDTO dto);

	RawMaterial getRawMaterialById(Long id);

	List<RawMaterial> getAllRawMaterials();

}

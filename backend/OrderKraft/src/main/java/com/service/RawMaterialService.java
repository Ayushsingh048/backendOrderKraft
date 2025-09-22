package com.service;

import java.util.List;

import com.dto.Raw_MaterialDTO;
import com.entity.RawMaterial;

public interface RawMaterialService {
	public RawMaterial createRawMaterial(Raw_MaterialDTO dto);

	RawMaterial getRawMaterialById(Long id);

	List<RawMaterial> getAllRawMaterials();
	
	//search endpoints
	List<RawMaterial> getRawMaterialsByName(String name);
    List<RawMaterial> getRawMaterialsByUnitCost(Double unitCost);
    List<RawMaterial> getRawMaterialsByStockQuantity(Long stockQuantity);
    List<RawMaterial> getRawMaterialsByStatus(String status);
    List<RawMaterial> getRawMaterialsBySupplier(Long supplierId);
    
    // update the supplier details 
    RawMaterial addSupplierToRawMaterial(Long rawMaterialId, Long supplierId);
    
    //reduce stock
    RawMaterial updateStockQuantity(Long rawMaterialId, Long quantityToReduce);


}

package com.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.Raw_MaterialDTO;

import com.entity.RawMaterial;

import com.service.RawMaterialService;


@RestController
@RequestMapping("/raw_material")
public class RawMaterialController {
	@Autowired
	private RawMaterialService rawMaterialService;
	
	
	@PostMapping("/add")
	public ResponseEntity<RawMaterial> createRawMaterial(@RequestBody Raw_MaterialDTO rawMaterialdto)
	{
		return ResponseEntity.ok( rawMaterialService.createRawMaterial(rawMaterialdto));
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<RawMaterial>> fetchAll()
	{
		return ResponseEntity.ok(rawMaterialService.getAllRawMaterials());
	}
	
//  Search Endpoints
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<RawMaterial>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(rawMaterialService.getRawMaterialsByName(name));
    }

    @GetMapping("/search/unitCost/{unitCost}")
    public ResponseEntity<List<RawMaterial>> getByUnitCost(@PathVariable Double unitCost) {
        return ResponseEntity.ok(rawMaterialService.getRawMaterialsByUnitCost(unitCost));
    }

    @GetMapping("/search/stockQuantity/{quantity}")
    public ResponseEntity<List<RawMaterial>> getByStockQuantity(@PathVariable Long quantity) {
        return ResponseEntity.ok(rawMaterialService.getRawMaterialsByStockQuantity(quantity));
    }

    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<RawMaterial>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(rawMaterialService.getRawMaterialsByStatus(status));
    }

    @GetMapping("/search/supplier/{supplierId}")
    public ResponseEntity<List<RawMaterial>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(rawMaterialService.getRawMaterialsBySupplier(supplierId));
    }
	
	//  Add supplier to an existing raw material
    @PutMapping("/{rawMaterialId}/addSupplier/{supplierId}")
    public ResponseEntity<RawMaterial> addSupplierToRawMaterial(
            @PathVariable Long rawMaterialId,
            @PathVariable Long supplierId) {
        return ResponseEntity.ok(rawMaterialService.addSupplierToRawMaterial(rawMaterialId, supplierId));
    }
    
    //Reduce stock
    @PutMapping("/{rawMaterialId}/reduceStock/{quantity}")
    public ResponseEntity<RawMaterial> reduceStock(
            @PathVariable Long rawMaterialId,
            @PathVariable Long quantity) {
        return ResponseEntity.ok(rawMaterialService.updateStockQuantity(rawMaterialId, quantity));
    }

}

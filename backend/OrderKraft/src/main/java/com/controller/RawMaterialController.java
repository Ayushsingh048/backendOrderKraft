package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.Raw_MaterialDTO;
import com.dto.SupplierDTO;
import com.entity.RawMaterial;
import com.entity.Supplier;
import com.service.RawMaterialService;
import com.service.SupplierService;

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
}

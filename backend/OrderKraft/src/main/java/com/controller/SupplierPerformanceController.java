package com.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.dto.Supplier_PerformanceDTO;
import com.entity.SupplierPerformance;
import com.service.SupplierPerformanceService;

@RestController
@RequestMapping("/supplier_performance")
public class SupplierPerformanceController {
	
	@Autowired
	SupplierPerformanceService supplierPerformanceService;
	
	@PostMapping("/add")
	public ResponseEntity<SupplierPerformance> createRawMaterial(@RequestBody Supplier_PerformanceDTO supplierPerformancedto)
	{
		return ResponseEntity.ok(supplierPerformanceService.createSupplierPerformance(supplierPerformancedto));
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<SupplierPerformance>> fetchAll()
	{
		return ResponseEntity.ok(supplierPerformanceService.getAllSupplierPerformance());
	}
	
	@GetMapping("/top3")
	public ResponseEntity<?> getTop3Suppliers(){
		System.out.println("Suppliers API.....................");
		return ResponseEntity.ok(supplierPerformanceService.getTop3Suppliers());
	}
}

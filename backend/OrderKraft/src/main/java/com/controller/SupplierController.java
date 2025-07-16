package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.SupplierDTO;
import com.entity.Supplier;
import com.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	
	@PostMapping("/add")
	public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierDTO supplierdto)
	{
		return ResponseEntity.ok( supplierService.createSupplier(supplierdto));
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Supplier>> fetchAll()
	{
		return ResponseEntity.ok(supplierService.getAllSupplier());
	}
	
}

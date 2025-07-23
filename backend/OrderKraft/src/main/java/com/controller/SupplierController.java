package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	//add new supplier
	@PostMapping("/add")
	public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierDTO supplierdto)
	{
		return ResponseEntity.ok( supplierService.createSupplier(supplierdto));
		
	}
	
	
	//get list of all supplier
	@GetMapping("/all")
	public ResponseEntity<List<Supplier>> fetchAll()
	{
		return ResponseEntity.ok(supplierService.getAllSupplier());
	}
	
	//find supplier by id
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Supplier>> getSuplierById(@PathVariable Long id)
	{
		return ResponseEntity.ok(supplierService.getSupplierById(id));
	}
	
	
	
}

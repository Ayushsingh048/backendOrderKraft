package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.dto.SupplierDTO;
import com.entity.Supplier;
import com.repository.SupplierRepository;

public class SupplierServiceImpl implements SupplierService {
	@Autowired
	SupplierRepository supplierRepo;
	
	@Override
	public Supplier createSupplier(SupplierDTO supplierdto) {
		// To save new supplier data
		Supplier supplier = new Supplier();
		supplier.setName(supplierdto.getName());
		supplier.setContact_person(supplierdto.getContact_person());
		supplier.setEmail(supplierdto.getEmail());
		supplier.setPhone(supplierdto.getPhone());
		supplier.setRating(supplierdto.getRating());
		return supplierRepo.save(supplier);
		 
	}

	@Override
	public List<Supplier> getAllSupplier() {
		// TO fetch all content of table
		return supplierRepo.findAll();
	}

	@Override
	public Optional<Supplier> getSupplierById(Long id) {
		// find supplier by id
		
		return supplierRepo.findById(id);
	}

}

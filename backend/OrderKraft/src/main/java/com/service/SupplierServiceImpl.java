package com.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.SupplierDTO;
import com.entity.Supplier;
import com.entity.RawMaterial;
import com.repository.RawMaterialRepository;
import com.repository.SupplierRepository;



@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	SupplierRepository supplierRepo;
	@Autowired
	private RawMaterialRepository rawMaterialRepository;

	
	@Override
	public Supplier createSupplier(SupplierDTO supplierdto) {
		// To save new supplier data
		Supplier supplier = new Supplier();
		supplier.setName(supplierdto.getName());
		supplier.setContact_person(supplierdto.getContact_person());
		supplier.setEmail(supplierdto.getEmail());
		supplier.setPhone(supplierdto.getPhone());
		supplier.setRating(supplierdto.getRating());
		
		if (supplierdto.getRawMaterialIds() != null && !supplierdto.getRawMaterialIds().isEmpty()) {
	        // Fetch RawMaterial entities by their IDs
	        Set<RawMaterial> rawMaterials = new HashSet<>(rawMaterialRepository.findAllById(supplierdto.getRawMaterialIds()));
	        supplier.setRawMaterials(rawMaterials);

	        // Maintain bidirectional association
	        for (RawMaterial rm : rawMaterials) {
	            rm.getSuppliers().add(supplier);
	        }
	    }
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

	@Override
	public void deleteSupplier(Long id) {
		// TODO Auto-generated method stub
		supplierRepo.deleteById(id);
	}




	@Override
	public Optional<Supplier> getSupplierByUserId(Long userId) {
		// TODO Auto-generated method stub
		System.out.println("inside supplier service");
//		Optional<Supplier> supplier= supplierRepo.findByUser_Id(userId);
		System.out.println(supplierRepo.findByUser_Id(userId));
		return supplierRepo.findByUser_Id(userId);
	}

}

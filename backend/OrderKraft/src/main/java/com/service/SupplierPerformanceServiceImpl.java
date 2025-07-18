package com.service;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.controller.SupplierPerformanceController;
import com.dto.Supplier_PerformanceDTO;
import com.entity.Supplier;
import com.entity.SupplierPerformance;
import com.repository.SupplierPerformanceRepository;
import com.repository.SupplierRepository;

@Service
public class SupplierPerformanceServiceImpl implements SupplierPerformanceService {
	
	@Autowired
	private SupplierPerformanceRepository supplierPerformanceRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Override
	public SupplierPerformance createSupplierPerformance(Supplier_PerformanceDTO supplierPerfDTO) {
		
		Optional<Supplier> supplier = supplierRepo.findById(supplierPerfDTO.getSupplier_id());
		
		if (supplier.isEmpty()) {
            throw new RuntimeException("Supplier not found with ID: " + supplierPerfDTO.getSupplier_id());
        }
		
		SupplierPerformance supplierPer = new SupplierPerformance();
		supplierPer.setDelivery_score(supplierPerfDTO.getDelivery_score());
		supplierPer.setCommunication_score(supplierPerfDTO.getCommunication_score());
		supplierPer.setQuality_score(supplierPerfDTO.getQuality_score());
		supplierPer.setSupplier(supplier.get());
		return supplierPerformanceRepo.save(supplierPer);
	}

	@Override
	public Optional<SupplierPerformance> getSupplierPerformanceById(Long id) {
		// find supplier by id
		return supplierPerformanceRepo.findById(id);
	}

	@Override
	public List<SupplierPerformance> getAllSupplierPerformance() {
		// TO fetch all content of table
		return supplierPerformanceRepo.findAll();
	}
	
	

}

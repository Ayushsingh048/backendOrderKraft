package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dto.Supplier_PerformanceDTO;
import com.entity.Supplier;
import com.entity.SupplierPerformance;
import com.repository.OrderRepository;
import com.repository.SupplierPerformanceRepository;
import com.repository.SupplierRepository;

@Service
public class SupplierPerformanceServiceImpl implements SupplierPerformanceService {
	
	@Autowired
	private SupplierPerformanceRepository supplierPerformanceRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
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
		supplierPer.setAverage_score();
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
	
	@Override
	public List<?> getTop3Suppliers(){
		
		List<SuppliersTop> suppliers = new ArrayList<>();
		
		for(SupplierPerformance sp: supplierPerformanceRepo.findTop5ByAverageScore()) {
			suppliers.add(new SuppliersTop(sp.getSupplier().getName(), orderRepo.countBySupplierId(sp.getSupplier().getSupplierId()), sp.getAverage_score()));
		}
		return suppliers;
	}
	

}

class SuppliersTop{
	String name;
	Long orders;
	Double averageScore;
	
	public Double getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(Double averageScore) {
		this.averageScore = averageScore;
	}
	public SuppliersTop(String name, Long orders, Double averageScore) {
		super();
		this.name = name;
		this.orders = orders;
		this.averageScore = averageScore;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOrders() {
		return orders;
	}
	public void setOrders(Long orders) {
		this.orders = orders;
	}
	
	
}

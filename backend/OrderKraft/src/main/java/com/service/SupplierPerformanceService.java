package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.Supplier_PerformanceDTO;
import com.entity.SupplierPerformance;

public interface SupplierPerformanceService {
	SupplierPerformance createSupplierPerformance(Supplier_PerformanceDTO supplierPerfDTO);
	Optional<SupplierPerformance> getSupplierPerformanceById(Long id);
	List<SupplierPerformance> getAllSupplierPerformance();
	List<?> getTop3Suppliers();
}

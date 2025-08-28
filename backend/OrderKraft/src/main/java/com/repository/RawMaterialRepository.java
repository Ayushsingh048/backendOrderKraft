package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
	 List<RawMaterial> findByName(String name);
	 List<RawMaterial> findByUnitCost(Double unitCost);
	 List<RawMaterial> findByStockQuantity(Long stockQuantity);
	 List<RawMaterial> findByStatus(String status);
	 List<RawMaterial> findBySuppliers_SupplierId(Long supplierId);

}

package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.SupplierPerformance;

public interface SupplierPerformanceRepository extends JpaRepository<SupplierPerformance, Long> {
	
	@Query("SELECT sp FROM SupplierPerformance sp ORDER BY sp.average_score DESC LIMIT 5")
	List<SupplierPerformance> findTop5ByAverageScore();
}

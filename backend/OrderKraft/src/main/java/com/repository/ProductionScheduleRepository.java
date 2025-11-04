package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.ProductionSchedule;
import java.util.List;

public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Long> {
	 // Method to find schedules by production manager ID
    @Query("SELECT ps FROM ProductionSchedule ps WHERE ps.productionManager.id = :managerId")
    List<ProductionSchedule> findByProductionManagerId(@Param("managerId") Long managerId);
}

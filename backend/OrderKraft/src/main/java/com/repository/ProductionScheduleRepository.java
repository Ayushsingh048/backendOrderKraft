package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.ProductionSchedule;
import com.entity.User;

import java.util.List;

public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Long> {
	 // Method to find schedules by production manager ID
    @Query("SELECT ps FROM ProductionSchedule ps WHERE ps.productionManager.id = :managerId")
    List<ProductionSchedule> findByProductionManagerId(@Param("managerId") Long managerId);
    
    
    @Query("SELECT TO_CHAR(p.endDate, 'Month'), COUNT(p) " +
            "FROM ProductionSchedule p " +
            "GROUP BY TO_CHAR(p.endDate, 'Month') " +
            "ORDER BY MIN(p.endDate)")
     List<Object[]> countSchedulesByMonth();
   // List<ProductionSchedule> findByStatus(String status);
}

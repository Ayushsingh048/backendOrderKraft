package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ProductionTask;

public interface ProductionTaskRepository extends JpaRepository<ProductionTask, Long> {

    // Find by production schedule ID
	List<ProductionTask> findByProductionSchedule_Id(Long scheduleId);


    // Find by name
    Optional<ProductionTask> findByName(String name);

    // Find by status
    List<ProductionTask> findByStatus(String status);
}

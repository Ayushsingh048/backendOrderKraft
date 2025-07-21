package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ProductionSchedule;

public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Long> {

    // Find by schedule ID (provided by JpaRepository)

    // Find by production manager (user) ID
	List<ProductionSchedule> findByUser_Id(Long id);


    // Find by status
    List<ProductionSchedule> findByStatus(String status);
}

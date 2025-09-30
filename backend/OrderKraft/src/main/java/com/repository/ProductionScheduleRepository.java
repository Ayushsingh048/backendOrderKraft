package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ProductionSchedule;

public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Long> {


    // Find by status
    List<ProductionSchedule> findByStatus(String status);
}

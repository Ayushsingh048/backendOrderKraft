package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.ProductionSchedule;

public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Long> {
}

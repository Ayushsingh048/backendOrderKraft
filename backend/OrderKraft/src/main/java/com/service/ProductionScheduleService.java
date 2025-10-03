package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.Production_ScheduleDTO;
import com.entity.ProductionSchedule;

public interface ProductionScheduleService {

    // Add a new production schedule
    ProductionSchedule createSchedule(Production_ScheduleDTO dto);

    // Retrieve all schedules
    List<ProductionSchedule> getAllSchedules();

    // Find a schedule by ID
    Optional<ProductionSchedule> getScheduleById(Long id);

    // Find schedules by status
    List<ProductionSchedule> getSchedulesByStatus(String status);
}

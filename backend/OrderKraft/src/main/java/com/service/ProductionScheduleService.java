package com.service;

import com.dto.Production_ScheduleDTO;
import com.entity.ProductionSchedule;

import java.util.List;

public interface ProductionScheduleService {
    ProductionSchedule createProductionSchedule(Production_ScheduleDTO dto);
    List<ProductionSchedule> getAllSchedules();
}

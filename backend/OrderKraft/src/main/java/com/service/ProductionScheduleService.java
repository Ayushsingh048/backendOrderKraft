package com.service;

import com.dto.Production_ScheduleDTO;
import com.dto.ScheduleResponseDTO;
import com.entity.ProductionSchedule;

import java.util.List;

public interface ProductionScheduleService {
    ProductionSchedule createProductionSchedule(Production_ScheduleDTO dto);
    List<ProductionSchedule> getAllSchedules();
    List<ScheduleResponseDTO> getAllSchedulesAsDTO(); // updated dto method 
    List<ScheduleResponseDTO> getSchedulesByManagerId(Long managerId); // search by manager id 

}

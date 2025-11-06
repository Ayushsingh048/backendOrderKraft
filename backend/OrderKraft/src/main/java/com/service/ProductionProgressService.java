package com.service;

import com.dto.ProductionProgressDTO;
import java.util.List;

public interface ProductionProgressService {

    ProductionProgressDTO getProgressForSchedule(Long scheduleId);

    List<ProductionProgressDTO> getProgressForAllSchedules();
}

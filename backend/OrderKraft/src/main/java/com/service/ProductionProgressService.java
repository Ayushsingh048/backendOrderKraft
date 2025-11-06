package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.ProductionProgressDTO;
import com.entity.ProductionSchedule;
import com.entity.ProductionTask;
import com.repository.ProductionScheduleRepository;
import com.repository.ProductionTaskRepository;

@Service
public class ProductionProgressService {

    private final ProductionScheduleRepository scheduleRepo;
    private final ProductionTaskRepository taskRepo;

    public ProductionProgressService(ProductionScheduleRepository scheduleRepo,
                                     ProductionTaskRepository taskRepo) {
        this.scheduleRepo = scheduleRepo;
        this.taskRepo = taskRepo;
    }

    public List<ProductionProgressDTO> getProgressForAllSchedules() {

        List<ProductionProgressDTO> progressList = new ArrayList<>();
        List<ProductionSchedule> schedules = scheduleRepo.findAll();

        for(ProductionSchedule schedule : schedules) {

            Long scheduleId = schedule.getId();

            // fetch tasks for this schedule
            List<ProductionTask> tasks = taskRepo.findByProductionSchedule_Id(scheduleId);

            long total = tasks.size();
            long completed = tasks.stream().filter(t -> "Completed".equalsIgnoreCase(t.getStatus())).count();

            double percentage = (total == 0) ? 0.0 : ((double) completed / total * 100);

            ProductionProgressDTO dto = new ProductionProgressDTO(
                    scheduleId,
                    total,
                    completed,
                    Math.round(percentage * 100.0) / 100.0 // round to 2 decimals
            );

            progressList.add(dto);
        }

        return progressList;
    }
}

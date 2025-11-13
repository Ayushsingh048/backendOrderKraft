package com.service;

import com.dto.ProductionProgressDTO;
import com.entity.ProductionSchedule;
import com.repository.ProductionScheduleRepository;
import com.repository.ProductionTaskRepository;
import com.service.ProductionProgressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductionProgressServiceImpl implements ProductionProgressService {

    private final ProductionTaskRepository taskRepository;
    private final ProductionScheduleRepository scheduleRepository;

    public ProductionProgressServiceImpl(ProductionTaskRepository taskRepository,
                                         ProductionScheduleRepository scheduleRepository) {
        this.taskRepository = taskRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ProductionProgressDTO getProgressForSchedule(Long scheduleId) {

        Long totalTasks = taskRepository.countByProductionSchedule_Id(scheduleId);
        Long completedTasks = taskRepository.countByProductionSchedule_IdAndStatus(scheduleId, "Completed");

        Double completionPercentage =
                (totalTasks == 0) ? 0.0 : (completedTasks * 100.0 / totalTasks);

        // Round to two decimals
        completionPercentage = Math.round(completionPercentage * 100.0) / 100.0;

        return new ProductionProgressDTO(
                scheduleId,
                totalTasks,
                completedTasks,
                completionPercentage
        );
    }

    @Override
    public List<ProductionProgressDTO> getProgressForAllSchedules() {
        List<ProductionSchedule> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .map(schedule -> getProgressForSchedule(schedule.getId()))
                .collect(Collectors.toList());
    }
}

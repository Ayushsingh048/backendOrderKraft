package com.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.ProductionReportDTO;
import com.entity.ProductionSchedule;
import com.entity.ProductionTask;
import com.repository.ProductionScheduleRepository;
import com.repository.ProductionTaskRepository;

@Service
public class ProductionReportServiceImpl implements ProductionReportService {

    @Autowired
    private ProductionScheduleRepository scheduleRepo;

    @Autowired
    private ProductionTaskRepository taskRepo;

    @Override
    public List<ProductionReportDTO> generateReport(LocalDate startDate, LocalDate endDate, String status) {
        // 1. Fetch schedules in the date range
        List<ProductionSchedule> schedules = scheduleRepo.findAll().stream()
                .filter(s -> !s.getStartDate().isBefore(startDate) && !s.getEndDate().isAfter(endDate))
                .collect(Collectors.toList());

        // 2. For each schedule, compute task statistics
        return schedules.stream().map(schedule -> {
            List<ProductionTask> tasks = taskRepo.findByProductionSchedule_ScheduleId(schedule.getScheduleId());
            
            if (status != null) {
                tasks = tasks.stream()
                        .filter(t -> t.getStatus().equalsIgnoreCase(status))
                        .collect(Collectors.toList());
            }

            long totalTasks = tasks.size();
            long completedTasks = tasks.stream()
                    .filter(t -> "completed".equalsIgnoreCase(t.getStatus()))
                    .count();
            double completionRate = totalTasks > 0 ? (completedTasks * 100.0) / totalTasks : 0.0;

            List<String> delayedTasks = tasks.stream()
                    .filter(t -> "delayed".equalsIgnoreCase(t.getStatus()))
                    .map(ProductionTask::getName)
                    .collect(Collectors.toList());

            return new ProductionReportDTO(
                    schedule.getStartDate(),
                    schedule.getEndDate(),
                    "Unit N/A",  // Placeholder if not linked yet
                    totalTasks,
                    completedTasks,
                    completionRate,
                    delayedTasks
            );
        }).collect(Collectors.toList());
    }
}

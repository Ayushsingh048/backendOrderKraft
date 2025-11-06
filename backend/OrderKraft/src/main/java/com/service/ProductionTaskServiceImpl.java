package com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Production_TaskDTO;
import com.dto.TaskResponseDTO;
import com.entity.ProductionSchedule;
import com.entity.ProductionTask;
import com.repository.ProductionScheduleRepository;
import com.repository.ProductionTaskRepository;

@Service
public class ProductionTaskServiceImpl implements ProductionTaskService {

    @Autowired
    private ProductionTaskRepository taskRepo;

    @Autowired
    private ProductionScheduleRepository scheduleRepo;

    @Override
    public ProductionTask createTask(Production_TaskDTO dto) {
        // Convert java.util.Date to java.time.LocalTime
        // LocalTime startTime = new java.sql.Time(dto.getStart_time().getTime()).toLocalTime();
       //  LocalTime endTime = new java.sql.Time(dto.getEnd_time().getTime()).toLocalTime();

        // Get the associated production schedule by ID
        ProductionSchedule schedule = scheduleRepo.findById(dto.getSchedule_id())
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + dto.getSchedule_id()));

        // Map DTO to entity
        ProductionTask task = new ProductionTask();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStart_time());
        task.setEndTime(dto.getEnd_time());
        task.setStatus(dto.getStatus());
        task.setProductionSchedule(schedule);

        return taskRepo.save(task);
    }

    @Override
    public List<ProductionTask> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public Optional<ProductionTask> getTaskById(Long id) {
        return taskRepo.findById(id);
    }

    @Override
    public List<ProductionTask> getTasksByScheduleId(Long scheduleId) {
        return taskRepo.findByProductionSchedule_Id(scheduleId);
    }

    @Override
    public Optional<ProductionTask> getTaskByName(String name) {
        return taskRepo.findByName(name);
    }

    @Override
    public List<ProductionTask> getTasksByStatus(String status) {
        return taskRepo.findByStatus(status);
    }
    
    //updated methods for tasks display
    @Override
    public List<TaskResponseDTO> getAllTasksAsDTO() {
        List<ProductionTask> tasks = taskRepo.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaskResponseDTO convertToDTO(ProductionTask task) {
        return new TaskResponseDTO(
            task.getTaskId(),
            task.getName(),
            task.getDescription(),
            task.getStartTime(),
            task.getEndTime(),
            task.getStatus(),
            task.getProductionSchedule() != null ? task.getProductionSchedule().getId() : null
        );
    }
    
}

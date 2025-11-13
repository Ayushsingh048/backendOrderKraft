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
    public TaskResponseDTO createTask(Production_TaskDTO dto) {
        // Get the associated production schedule by ID
        ProductionSchedule schedule = scheduleRepo.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + dto.getScheduleId()));

        // Map DTO to entity
        ProductionTask task = new ProductionTask();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStartTime());
        task.setEndTime(dto.getEndTime());
        task.setStatus(dto.getStatus());
        task.setProductionSchedule(schedule);

        // Save the entity
        ProductionTask savedTask = taskRepo.save(task);
        
        // Convert to DTO and return
        return convertToDTO(savedTask);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<ProductionTask> tasks = taskRepo.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskResponseDTO> getTaskById(Long id) {
        return taskRepo.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<TaskResponseDTO> getTasksByScheduleId(Long scheduleId) {
        List<ProductionTask> tasks = taskRepo.findByProductionSchedule_Id(scheduleId);
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskResponseDTO> getTaskByName(String name) {
        return taskRepo.findByName(name)
                .map(this::convertToDTO);
    }

    @Override
    public List<TaskResponseDTO> getTasksByStatus(String status) {
        List<ProductionTask> tasks = taskRepo.findByStatus(status);
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public TaskResponseDTO updateTaskSchedule(Long taskId, Long scheduleId) {
        // Find the existing task
        ProductionTask task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        // Find the new schedule
        ProductionSchedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + scheduleId));

        // Update the schedule
        task.setProductionSchedule(schedule);

        // Save the updated task
        ProductionTask updatedTask = taskRepo.save(task);

        // Convert to DTO and return
        return convertToDTO(updatedTask);
    }

    // Private helper method to convert entity to DTO
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
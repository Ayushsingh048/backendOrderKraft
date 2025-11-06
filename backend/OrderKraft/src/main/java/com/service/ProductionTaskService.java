package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.Production_TaskDTO;
import com.dto.TaskResponseDTO;
import com.entity.ProductionTask;

public interface ProductionTaskService {

    // Add a new production task
    ProductionTask createTask(Production_TaskDTO dto);

    // Retrieve all tasks
    List<ProductionTask> getAllTasks();

    // Find by task ID
    Optional<ProductionTask> getTaskById(Long id);

    // Find by schedule ID
    List<ProductionTask> getTasksByScheduleId(Long scheduleId);

    // Find by name
    Optional<ProductionTask> getTaskByName(String name);

    // Find by status
    List<ProductionTask> getTasksByStatus(String status);

	List<TaskResponseDTO> getAllTasksAsDTO(); // updated method for tasks display
}

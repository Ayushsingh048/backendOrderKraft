package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.Production_TaskDTO;
import com.dto.TaskResponseDTO;

public interface ProductionTaskService {

    // Add a new production task - return DTO instead of entity
    TaskResponseDTO createTask(Production_TaskDTO dto);

    // Retrieve all tasks - return DTOs instead of entities
    List<TaskResponseDTO> getAllTasks();

    // Find by task ID - return DTO instead of entity
    Optional<TaskResponseDTO> getTaskById(Long id);

    // Find by schedule ID - return DTOs instead of entities
    List<TaskResponseDTO> getTasksByScheduleId(Long scheduleId);

    // Find by name - return DTO instead of entity
    Optional<TaskResponseDTO> getTaskByName(String name);

    // Find by status - return DTOs instead of entities
    List<TaskResponseDTO> getTasksByStatus(String status);


}
package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.Production_TaskDTO;
import com.dto.TaskResponseDTO;
import com.service.ProductionTaskService;

@RestController
@RequestMapping("/production_task")
public class ProductionTaskController {

    @Autowired
    private ProductionTaskService taskService;

    // Add a new production task
    @PostMapping("/add")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody Production_TaskDTO dto) {
        try {
            TaskResponseDTO createdTask = taskService.createTask(dto);
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            System.err.println("Error creating task: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get all production tasks
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        try {
            List<TaskResponseDTO> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            System.err.println("Error fetching tasks: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get a task by its ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        try {
            return taskService.getTaskById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("Error fetching task by ID: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get tasks by schedule ID
    @GetMapping("/search/schedule/{scheduleId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByScheduleId(@PathVariable Long scheduleId) {
        try {
            List<TaskResponseDTO> tasks = taskService.getTasksByScheduleId(scheduleId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            System.err.println("Error fetching tasks by schedule: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get a task by its name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<TaskResponseDTO> getTaskByName(@PathVariable String name) {
        try {
            return taskService.getTaskByName(name)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("Error fetching task by name: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get tasks by status
    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(@PathVariable String status) {
        try {
            List<TaskResponseDTO> tasks = taskService.getTasksByStatus(status);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            System.err.println("Error fetching tasks by status: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
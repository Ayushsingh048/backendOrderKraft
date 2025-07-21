package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.Production_TaskDTO;
import com.entity.ProductionTask;
import com.service.ProductionTaskService;

@RestController
@RequestMapping("/production_task")
public class ProductionTaskController {

    @Autowired
    private ProductionTaskService taskService;

    // Add a new production task
    @PostMapping("/add")
    public ResponseEntity<ProductionTask> createTask(@RequestBody Production_TaskDTO dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
    }

    // Get all production tasks
    @GetMapping("/all")
    public ResponseEntity<List<ProductionTask>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // Get a task by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductionTask> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get tasks by schedule ID
    @GetMapping("/search/schedule/{scheduleId}")
    public ResponseEntity<List<ProductionTask>> getTasksByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(taskService.getTasksByScheduleId(scheduleId));
    }

    // Get a task by its name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<ProductionTask> getTaskByName(@PathVariable String name) {
        return taskService.getTaskByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get tasks by status
    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<ProductionTask>> getTasksByStatus(@PathVariable String status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }
}

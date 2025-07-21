package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.Production_ScheduleDTO;
import com.entity.ProductionSchedule;
import com.service.ProductionScheduleService;

@RestController
@RequestMapping("/production_schedule")
public class ProductionScheduleController {

    @Autowired
    private ProductionScheduleService scheduleService;

    // Add a new schedule
    @PostMapping("/add")
    public ResponseEntity<ProductionSchedule> createSchedule(@RequestBody Production_ScheduleDTO dto) {
        return ResponseEntity.ok(scheduleService.createSchedule(dto));
    }

    // Get all schedules
    @GetMapping("/all")
    public ResponseEntity<List<ProductionSchedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    // Get a schedule by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductionSchedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get schedules by manager (user) ID
    @GetMapping("/search/production_manager/{productionManagerId}")
    public ResponseEntity<List<ProductionSchedule>> getSchedulesByManagerId(@PathVariable Long productionManagerId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByProductionManagerId(productionManagerId));
    }

    // Get schedules by status
    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<ProductionSchedule>> getSchedulesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(scheduleService.getSchedulesByStatus(status));
    }
}

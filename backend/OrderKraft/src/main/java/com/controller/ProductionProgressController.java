package com.controller;

import com.dto.ProductionProgressDTO;
import com.service.ProductionProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production")
@CrossOrigin(origins = "*")
public class ProductionProgressController {

    private final ProductionProgressService progressService;

    public ProductionProgressController(ProductionProgressService progressService) {
        this.progressService = progressService;
    }

    // Progress for a single schedule
    @GetMapping("/schedules/{scheduleId}/progress")
    public ResponseEntity<ProductionProgressDTO> getProgressForSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(progressService.getProgressForSchedule(scheduleId));
    }

    // Progress for all schedules
    @GetMapping("/schedules/progress")
    public ResponseEntity<List<ProductionProgressDTO>> getAllScheduleProgress() {
        return ResponseEntity.ok(progressService.getProgressForAllSchedules());
    }
}

package com.controller;

import com.dto.Production_ScheduleDTO;
import com.entity.BOM;
import com.entity.BOM_Material;
import com.entity.InventoryRawMaterial;
import com.entity.ProductionSchedule;
import com.repository.BOMRepository;
import com.repository.InventoryRawMaterialRepository;
import com.service.ProductionScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/production-schedule")
public class ProductionScheduleController {

    @Autowired
    private ProductionScheduleService scheduleService;

    @Autowired
    private BOMRepository bomRepository;

    @Autowired
    private InventoryRawMaterialRepository inventoryRepo;

    // ✅ Create a new production schedule
    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody Production_ScheduleDTO dto) {
        System.out.println("=== [START] createSchedule() ===");
        try {
            ProductionSchedule schedule = scheduleService.createProductionSchedule(dto);
            System.out.println("✅ Schedule created successfully with ID: " + schedule.getId());
            System.out.println("=== [END] createSchedule() ===");

            // Return both message + schedule details
            return ResponseEntity.ok("✅ Production schedule created successfully! Schedule ID: " + schedule.getId());

        } catch (Exception e) {
            System.out.println("❌ Exception in createSchedule(): " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error creating schedule: " + e.getMessage());
        }
    }


    // ✅ Get all production schedules

    
    @GetMapping("/all")
    public ResponseEntity<List<ProductionSchedule>> getAllSchedules() {
        List<ProductionSchedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

}

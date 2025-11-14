package com.controller;

import com.dto.Production_ScheduleDTO;
import com.dto.ScheduleResponseDTO;
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
import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.Authentication;

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
    public ResponseEntity<?> createSchedule(@RequestBody Production_ScheduleDTO dto , Authentication authentication) {
        System.out.println("=== [START] createSchedule() ===");
        try {String username = authentication.getName();
        System.out.println(">>> Logged-in Production Manager: " + username);
        System.out.println("product_id"+dto.getProductId());
            ProductionSchedule schedule = scheduleService.createProductionSchedule(dto,username);
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

    
//    @GetMapping("/all")
//    public ResponseEntity<List<ProductionSchedule>> getAllSchedules() {
//        List<ProductionSchedule> schedules = scheduleService.getAllSchedules();
//        return ResponseEntity.ok(schedules);
//    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        try {
            List<ScheduleResponseDTO> schedules = scheduleService.getAllSchedulesAsDTO();
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            System.err.println("Error fetching schedules: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    // ✅ Get schedules by production manager ID
    @GetMapping("/search/manager/{managerId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByManagerId(@PathVariable Long managerId) {
        try {
            List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesByManagerId(managerId);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            System.err.println("Error fetching schedules for manager " + managerId + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    //get number of completed orders against the months
    @GetMapping("/monthly-count")
    public Map<String, Long> getSchedulesCountByMonth() {
        return scheduleService.getSchedulesCountByMonth();
    }
    
}

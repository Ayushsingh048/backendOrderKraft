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
        try {
            Optional<BOM> bomOpt = bomRepository.findById(dto.getBomId());
            if (!bomOpt.isPresent()) {
                return ResponseEntity.badRequest().body("BOM not found with ID: " + dto.getBomId());
            }

            BOM bom = bomOpt.get();

            // 🔹 Check if sufficient raw materials are available
            boolean isMaterialAvailable = true;

            for (BOM_Material material : bom.getMaterials()) {
                InventoryRawMaterial rawMaterial = material.getRawmaterial();
                double requiredQty = material.getQntperunit() * dto.getQuantityToProduce();

                if (rawMaterial == null || rawMaterial.getQuantity() < requiredQty) {
                    isMaterialAvailable = false;
                    break;
                }
            }

            if (!isMaterialAvailable) {
                return ResponseEntity.badRequest().body("Not enough raw materials in inventory to create schedule.");
            }

            // 🔹 Deduct used materials from inventory
            for (BOM_Material material : bom.getMaterials()) {
                InventoryRawMaterial rawMaterial = material.getRawmaterial();
                double requiredQty = material.getQntperunit() * dto.getQuantityToProduce();
                rawMaterial.setQuantity( rawMaterial.getQuantity() - requiredQty);
                inventoryRepo.save(rawMaterial);
            }

            ProductionSchedule schedule = scheduleService.createProductionSchedule(dto);
            return ResponseEntity.ok(schedule);

        } catch (Exception e) {
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

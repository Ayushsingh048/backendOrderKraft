package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.Production_UnitDTO;
import com.entity.ProductionUnit;
import com.service.ProductionUnitService;


@RestController
@RequestMapping("/production_unit")
public class ProductionUnitController {

    @Autowired
    private ProductionUnitService productionUnitService;

   // Add a new production unit.
    @PostMapping("/add")
    public ResponseEntity<ProductionUnit> createProductionUnit(@RequestBody Production_UnitDTO dto) {
        return ResponseEntity.ok(productionUnitService.createProductionUnit(dto));
    }

   // Fetch all production units.
    @GetMapping("/all")
    public ResponseEntity<List<ProductionUnit>> getAllProductionUnits() {
        return ResponseEntity.ok(productionUnitService.getAllProductionUnits());
    }

    //Fetch a production unit by its unique ID.
    @GetMapping("/{id}")
    public ResponseEntity<ProductionUnit> getProductionUnitById(@PathVariable Long id) {
        return productionUnitService.getProductionUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   //Search production unit by name.
    @GetMapping("/search/name/{name}")
    public ResponseEntity<ProductionUnit> getProductionUnitByName(@PathVariable String name) {
        return productionUnitService.getProductionUnitByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   //Search units by their capacity value.
    @GetMapping("/search/capacity/{capacity}")
    public ResponseEntity<List<ProductionUnit>> getProductionUnitsByCapacity(@PathVariable long capacity) {
        return ResponseEntity.ok(productionUnitService.getProductionUnitsByCapacity(capacity));
    }

   // Search units by assigned task ID.
    @GetMapping("/search/task/{taskId}")
    public ResponseEntity<List<ProductionUnit>> getProductionUnitsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(productionUnitService.getProductionUnitsByTaskId(taskId));
    }

  // Search units by production manager ID.
    @GetMapping("/search/production_manager/{productionManagerId}")
    public ResponseEntity<List<ProductionUnit>> getProductionUnitsByProductionManagerId(@PathVariable Long productionManagerId) {
        return ResponseEntity.ok(productionUnitService.getProductionUnitsByProductionManagerId(productionManagerId));
    }
    
    // Update task ID for a specific unit
    @PutMapping("/update_taskid/{unitId}/{taskid}")
    public ResponseEntity<ProductionUnit> updateTaskId(@PathVariable Long unitId, @PathVariable Long taskid) {
        try {
            ProductionUnit updatedUnit = productionUnitService.updateTaskId(unitId, taskid);
            return ResponseEntity.ok(updatedUnit);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

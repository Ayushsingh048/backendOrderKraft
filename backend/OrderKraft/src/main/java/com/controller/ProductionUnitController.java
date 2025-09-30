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

    // Add a new production unit
    @PostMapping("/add")
    public ResponseEntity<ProductionUnit> createProductionUnit(@RequestBody Production_UnitDTO dto) {
        return ResponseEntity.ok(productionUnitService.createProductionUnit(dto));
    }

    // Get all production units
    @GetMapping("/all")
    public ResponseEntity<List<ProductionUnit>> getAllProductionUnits() {
        return ResponseEntity.ok(productionUnitService.getAllProductionUnits());
    }

    // Get a production unit by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductionUnit> getProductionUnitById(@PathVariable Long id) {
        return productionUnitService.getProductionUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get a production unit by name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<ProductionUnit> getProductionUnitByName(@PathVariable String name) {
        return productionUnitService.getProductionUnitByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

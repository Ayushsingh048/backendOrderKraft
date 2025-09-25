package com.controller;

import com.dto.InventoryRawMaterialDTO;
import com.entity.InventoryRawMaterial;
import com.service.InventoryRawMaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/inventory_rawmaterial")
public class InventoryRawMaterialController {

    @Autowired
    private InventoryRawMaterialService service;

    @PostMapping("/add")
    public ResponseEntity<InventoryRawMaterial> add(@RequestBody InventoryRawMaterialDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryRawMaterial>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryRawMaterial> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<InventoryRawMaterial>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<InventoryRawMaterial>> searchByDescription(@RequestParam String description) {
        return ResponseEntity.ok(service.searchByDescription(description));
    }

    @GetMapping("/search/last_updated")
    public ResponseEntity<List<InventoryRawMaterial>> searchByLastUpdated(@RequestParam String date) {
        LocalDate parsed = LocalDate.parse(date);
        return ResponseEntity.ok(service.searchByLastUpdated(parsed));
    }

    @GetMapping("/search/category_id")
    public ResponseEntity<List<InventoryRawMaterial>> searchByCategoryId(@RequestParam Long categoryId) {
        return ResponseEntity.ok(service.searchByCategoryId(categoryId));
    }

    @PutMapping("/update/add_quantity")
    public ResponseEntity<InventoryRawMaterial> updateQuantity(@RequestParam String name,
                                                               @RequestParam int quantity) {
        return ResponseEntity.ok(service.updateQuantityByName(name, quantity));
    }
}

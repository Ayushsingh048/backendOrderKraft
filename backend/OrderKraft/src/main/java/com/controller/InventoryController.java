package com.controller;

import com.dto.InventoryDTO;
import com.entity.Inventory;
import com.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Add new inventory
    @PostMapping("/add")
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.createInventory(dto));
    }

    // Get all inventory
    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    // Get inventory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

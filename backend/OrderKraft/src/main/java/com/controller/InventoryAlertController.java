package com.controller;

import com.dto.Inventory_AlertDTO;
import com.entity.Inventory_alert;
import com.service.InventoryAlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory_alert") // Base URL
public class InventoryAlertController {

    @Autowired
    private InventoryAlertService alertService;

    // Add new inventory alert
    @PostMapping("/add")
    public ResponseEntity<Inventory_alert> createAlert(@RequestBody Inventory_AlertDTO dto) {
        return ResponseEntity.ok(alertService.createInventoryAlert(dto));
    }

    // Get all inventory alerts
    @GetMapping("/all")
    public ResponseEntity<List<Inventory_alert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllInventoryAlerts());
    }

    // Get alert by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory_alert> getAlertById(@PathVariable Long id) {
        return alertService.getInventoryAlertById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

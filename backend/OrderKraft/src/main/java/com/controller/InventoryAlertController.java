package com.controller;

import com.entity.Inventory_alert;
import com.service.InventoryAlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class InventoryAlertController {

	@Autowired
    private InventoryAlertService alertService;

    //Fetch only active (unresolved) alerts
    @GetMapping("/active")
    public List<Inventory_alert> getActiveAlerts() {
        return alertService.getActiveAlerts();
    }

    //Manually resolve an alert(Optional)
    @PutMapping("/{id}/resolve")
    public String resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return "Alert resolved successfully";
    }
}

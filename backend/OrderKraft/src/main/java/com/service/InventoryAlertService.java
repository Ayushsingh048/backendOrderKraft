package com.service;

import com.dto.Inventory_AlertDTO;
import com.entity.Inventory_alert;

import java.util.List;
import java.util.Optional;

public interface InventoryAlertService {
    Inventory_alert createInventoryAlert(Inventory_AlertDTO dto); // Add alert
    List<Inventory_alert> getAllInventoryAlerts();                // Get all
    Optional<Inventory_alert> getInventoryAlertById(Long id);     // Get by ID
}

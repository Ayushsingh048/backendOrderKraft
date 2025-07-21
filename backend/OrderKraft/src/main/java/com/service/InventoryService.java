package com.service;

import com.dto.InventoryDTO;
import com.entity.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    Inventory createInventory(InventoryDTO dto);         // Add inventory record
    List<Inventory> getAllInventory();                   // Fetch all inventory records
    Optional<Inventory> getInventoryById(Long id);       // Get inventory by ID
}

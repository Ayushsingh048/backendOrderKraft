package com.service;

import com.entity.Inventory;
import com.entity.InventoryRawMaterial;
import com.entity.Inventory_alert;
import com.repository.InventoryAlertRepository;
import com.repository.InventoryRawMaterialRepository;
import com.repository.InventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryAlertServiceImpl implements InventoryAlertService {

    @Autowired
    private InventoryAlertRepository alertRepo;
    
    @Autowired
    private InventoryRepository inventoryRepo;
    
    @Autowired
    private InventoryRawMaterialRepository rawMaterialRepo;

    @Override
    public void checkLowStockAndUpdateAlerts() {
        // 1. Check finished product inventory
        List<Inventory> productInventories = inventoryRepo.findAll();
        for (Inventory inv : productInventories) {
            if (inv.getQuantity() < inv.getLowStockThreshold()) {
                createOrUpdateAlert(inv, null, "LOW_STOCK");
            }
        }

        // 2. Check raw material inventory
        List<InventoryRawMaterial> rawInventories = rawMaterialRepo.findAll();
        for (InventoryRawMaterial raw : rawInventories) {
            if (raw.getQuantity() < 20) {
                createOrUpdateAlert(null, raw, "LOW_STOCK");
            }
        }
    }
    
    // Helper: Create a new alert or update an existing unresolved one.
    private void createOrUpdateAlert(Inventory inventory, InventoryRawMaterial rawMaterial, String alertType) {
        Inventory_alert alert = new Inventory_alert();
        alert.setInventory(inventory);
        alert.setInventoryRawMaterial(rawMaterial);
        alert.setAlert_type(alertType);
        alert.setTrigger_date(LocalDateTime.now());
        alert.setResolved(false);

        alertRepo.save(alert);
    }

    @Override
    public List<Inventory_alert> getActiveAlerts() {
        return alertRepo.findByResolvedFalse();
    }
    
    @Override
    public void resolveAlert(Long id) {
        Inventory_alert alert = alertRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setResolved(true);
        alertRepo.save(alert);
    }
}

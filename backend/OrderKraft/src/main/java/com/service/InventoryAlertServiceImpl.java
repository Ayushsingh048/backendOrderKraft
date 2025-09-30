package com.service;

import com.dto.InventoryAlertDTO;
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
import java.util.stream.Collectors;

@Service
public class InventoryAlertServiceImpl implements InventoryAlertService {

    @Autowired
    private InventoryAlertRepository alertRepo;

    @Autowired
    private InventoryRepository inventoryRepo;

    @Autowired
    private InventoryRawMaterialRepository rawMaterialRepo;
    
    @Autowired
    private NotificationService notificationService;

    @Override
    public void checkLowStockAndUpdateAlerts() {
        // --- 1. Finished product inventory ---
        List<Inventory> productInventories = inventoryRepo.findAll();
        for (Inventory inv : productInventories) {
            boolean alertExists = alertRepo.existsByInventoryAndResolvedFalse(inv);

            if (inv.getQuantity() < inv.getLowStockThreshold()) {
                if (!alertExists) {
                    Inventory_alert alert = new Inventory_alert();
                    alert.setInventory(inv);
                    alert.setAlert_type("LOW_STOCK");
                    alert.setTrigger_date(LocalDateTime.now());
                    alert.setResolved(false);
                    alertRepo.save(alert);
                    
                 // 🔔 Create notification for responsible roles
                    String title = "Low Stock Alert: " + inv.getProduct().getName();
                    String message = "Stock is below threshold. Current qty: " + inv.getQuantity();
<<<<<<< HEAD
                    notificationService.createNotificationForRole(title, message, "Inventory Manager");
=======
                    notificationService.createNotificationForRole(title, message, "ADMIN");
                    notificationService.createNotificationForRole(title, message, "INVENTORY MANAGER");
>>>>>>> 4daa46e5e2e33425cb672a1e921f0f61803613d8
                }
            } else {
                // Auto-resolve if stock is sufficient
                if (alertExists) {
                    List<Inventory_alert> activeAlerts = alertRepo.findByInventoryAndResolvedFalse(inv);
                    for (Inventory_alert alert : activeAlerts) {
                        alert.setResolved(true);
                        alertRepo.save(alert);
                    }
                }
            }
        }

        // --- 2. Raw material inventory ---
        List<InventoryRawMaterial> rawInventories = rawMaterialRepo.findAll();
        for (InventoryRawMaterial raw : rawInventories) {
            boolean alertExists = alertRepo.existsByInventoryRawMaterialAndResolvedFalse(raw);

            if (raw.getQuantity() < 20) { // raw material threshold
                if (!alertExists) {
                    Inventory_alert alert = new Inventory_alert();
                    alert.setInventoryRawMaterial(raw);
                    alert.setAlert_type("LOW_STOCK");
                    alert.setTrigger_date(LocalDateTime.now());
                    alert.setResolved(false);
                    alertRepo.save(alert);
                    
                 // 🔔 Create notification for responsible roles
                    String title = "Low Stock Alert: " + raw.getName();
                    String message = "Stock is below threshold. Current qty: " + raw.getQuantity();
                    notificationService.createNotificationForRole(title, message, "Inventory Manager");
                }
            } else {
                // Auto-resolve if stock is sufficient
                if (alertExists) {
                    List<Inventory_alert> activeAlerts = alertRepo.findByInventoryRawMaterialAndResolvedFalse(raw);
                    for (Inventory_alert alert : activeAlerts) {
                        alert.setResolved(true);
                        alertRepo.save(alert);
                    }
                }
            }
        }
    }

    @Override
    public List<InventoryAlertDTO> getActiveAlerts() {
        return alertRepo.findByResolvedFalse()
        		.stream()
                .map(alert -> new InventoryAlertDTO(
                        alert.getInventory() != null ? alert.getInventory().getProduct().getName() : null,
                        alert.getInventoryRawMaterial() != null ? alert.getInventoryRawMaterial().getName() : null,
                        alert.getAlert_type(),
                        alert.getTrigger_date(),
                        alert.getInventory() != null ? alert.getInventory().getQuantity() : null,
                        alert.getInventoryRawMaterial() != null ? alert.getInventoryRawMaterial().getQuantity() : null
                    ))
                    .collect(Collectors.toList());
    }

    @Override
    public java.util.Optional<Inventory_alert> getInventoryAlertById(Long id) {
        return alertRepo.findById(id);
    }

    @Override
    public void resolveAlert(Long id) {
        Inventory_alert alert = alertRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setResolved(true);
        alertRepo.save(alert);
    }
}

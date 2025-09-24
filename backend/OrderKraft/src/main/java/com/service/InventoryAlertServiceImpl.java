package com.service;

import com.entity.Inventory;
import com.entity.Inventory_alert;
import com.repository.InventoryAlertRepository;
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

    @Override
    public void checkLowStockAndUpdateAlerts() {
        List<Inventory> inventories = inventoryRepo.findAll();

        for (Inventory inv : inventories) {
            boolean alertExists = alertRepo.existsByProductAndResolvedFalse(inv.getProduct());

            if (inv.getQuantity() < inv.getLowStockThreshold()) {
                // Create new alert if it doesn't exist
                if (!alertExists) {
                    Inventory_alert alert = new Inventory_alert();
                    alert.setProduct(inv.getProduct());
                    alert.setAlert_type("LOW_STOCK");
                    alert.setTrigger_date(LocalDateTime.now());
                    alert.setResolved(false);

                    alertRepo.save(alert);
                }
            } else {
                // Auto-resolve existing alert if stock is back to normal
                if (alertExists) {
                    List<Inventory_alert> activeAlerts = alertRepo.findByProductAndResolvedFalse(inv.getProduct());
                    for (Inventory_alert alert : activeAlerts) {
                        alert.setResolved(true);
                        alertRepo.save(alert);
                    }
                }
            }
        }
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

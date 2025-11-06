package com.repository;

import com.entity.Inventory;
import com.entity.InventoryRawMaterial;
import com.entity.Inventory_alert;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAlertRepository extends JpaRepository<Inventory_alert, Long> {

	// --- Active alerts ---
    List<Inventory_alert> findByResolvedFalse();
    
    List<Inventory_alert> findByInventoryRawMaterialIsNullAndResolvedFalse();

    // --- Check if an active alert exists for a specific product inventory ---
    boolean existsByInventoryAndResolvedFalse(Inventory inventory);

    // --- Check if an active alert exists for a specific raw material inventory ---
    boolean existsByInventoryRawMaterialAndResolvedFalse(InventoryRawMaterial rawMaterial);

    // --- Fetch all active alerts for a specific product inventory ---
    List<Inventory_alert> findByInventoryAndResolvedFalse(Inventory inventory);

    // --- Fetch all active alerts for a specific raw material inventory ---
    List<Inventory_alert> findByInventoryRawMaterialAndResolvedFalse(InventoryRawMaterial rawMaterial);


}

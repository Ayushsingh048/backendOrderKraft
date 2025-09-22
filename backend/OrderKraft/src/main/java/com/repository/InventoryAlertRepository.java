package com.repository;

import com.entity.Inventory_alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAlertRepository extends JpaRepository<Inventory_alert, Long> {
    // No need to define findById — it's inherited from JpaRepository
}

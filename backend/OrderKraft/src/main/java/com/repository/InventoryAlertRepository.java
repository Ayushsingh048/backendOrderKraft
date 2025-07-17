package com.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.entity.Inventory_alert;

public interface InventoryAlertRepository extends JpaRepository<Inventory_alert, Long> {
}

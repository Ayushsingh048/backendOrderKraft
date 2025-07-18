package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Inherited: findById(), findAll(), save()
}

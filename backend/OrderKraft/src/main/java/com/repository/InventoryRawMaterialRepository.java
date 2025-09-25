package com.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.InventoryRawMaterial;

public interface InventoryRawMaterialRepository extends JpaRepository<InventoryRawMaterial, Long> {
    List<InventoryRawMaterial> findByNameContainingIgnoreCase(String name);
    List<InventoryRawMaterial> findByDescriptionContainingIgnoreCase(String description);
    List<InventoryRawMaterial> findByLastUpdated(LocalDate lastUpdated);
    List<InventoryRawMaterial> findByCategoryId(Long categoryId);

    InventoryRawMaterial findByName(String name);
}

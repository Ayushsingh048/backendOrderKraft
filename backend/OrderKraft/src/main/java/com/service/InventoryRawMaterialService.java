package com.service;

import com.dto.InventoryRawMaterialDTO;
import com.entity.InventoryRawMaterial;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryRawMaterialService {
    InventoryRawMaterial create(InventoryRawMaterialDTO dto);
    List<InventoryRawMaterial> getAll();
    Optional<InventoryRawMaterial> getById(Long id);

    List<InventoryRawMaterial> searchByName(String name);
    List<InventoryRawMaterial> searchByDescription(String description);
    List<InventoryRawMaterial> searchByLastUpdated(LocalDate lastUpdated);
    List<InventoryRawMaterial> searchByCategoryId(Long categoryId);
    List<InventoryRawMaterial> searchByInventoryManagerId(Long inventoryManagerId);

    InventoryRawMaterial updateQuantityByName(String name, int addQuantity);
}

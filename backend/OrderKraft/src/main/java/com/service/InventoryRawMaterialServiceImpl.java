package com.service;

import com.dto.InventoryRawMaterialDTO;
import com.entity.InventoryRawMaterial;
import com.repository.InventoryRawMaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryRawMaterialServiceImpl implements InventoryRawMaterialService {

    @Autowired
    private InventoryRawMaterialRepository repo;

    @Override
    public InventoryRawMaterial create(InventoryRawMaterialDTO dto) {
        InventoryRawMaterial raw = new InventoryRawMaterial();
        raw.setName(dto.getName());
        raw.setDescription(dto.getDescription());
        raw.setLastUpdated(dto.getLast_updated());
        raw.setCategoryId(dto.getCategory_id());
        raw.setInventoryManagerId(dto.getInventory_manager_id());
        raw.setQuantity(dto.getQuantity());
        return repo.save(raw);
    }

    @Override
    public List<InventoryRawMaterial> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<InventoryRawMaterial> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<InventoryRawMaterial> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<InventoryRawMaterial> searchByDescription(String description) {
        return repo.findByDescriptionContainingIgnoreCase(description);
    }

    @Override
    public List<InventoryRawMaterial> searchByLastUpdated(LocalDate lastUpdated) {
        return repo.findByLastUpdated(lastUpdated);
    }

    @Override
    public List<InventoryRawMaterial> searchByCategoryId(Long categoryId) {
        return repo.findByCategoryId(categoryId);
    }

    @Override
    public List<InventoryRawMaterial> searchByInventoryManagerId(Long inventoryManagerId) {
        return repo.findByInventoryManagerId(inventoryManagerId);
    }

    @Override
    public InventoryRawMaterial updateQuantityByName(String name, int addQuantity) {
        InventoryRawMaterial raw = repo.findByName(name);
        if (raw == null) throw new RuntimeException("Raw material not found in inventory: " + name);

        raw.setQuantity(raw.getQuantity() + addQuantity);
        raw.setLastUpdated(LocalDate.now());
        return repo.save(raw);
    }
}

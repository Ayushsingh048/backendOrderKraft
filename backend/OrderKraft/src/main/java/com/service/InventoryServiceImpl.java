package com.service;

import com.dto.InventoryDTO;
import com.entity.Inventory;
import com.entity.Product;
import com.repository.InventoryRepository;
import com.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepo;

    @Autowired
    private ProductRepository productRepo;

    @Override
    public Inventory createInventory(InventoryDTO dto) {
        // Fetch Product
        Product product = productRepo.findById(dto.getProduct_id())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Map DTO to Entity
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity((int) dto.getQuantity());
        inventory.setLastUpdated(dto.getLast_updated());

        return inventoryRepo.save(inventory);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepo.findAll();
    }

    @Override
    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepo.findById(id);
    }
}

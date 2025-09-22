package com.service;

import com.dto.InventoryDTO;
import com.entity.Inventory;
import com.entity.Product;
import com.entity.User;
import com.repository.InventoryRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;

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

    @Autowired
    private UserRepository userRepo;

    @Override
    public Inventory createInventory(InventoryDTO dto) {
        // Fetch Product
        Product product = productRepo.findById(dto.getProduct_id())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Fetch User
        User manager = userRepo.findById(dto.getInventory_manager_id())
                .orElseThrow(() -> new RuntimeException("Inventory Manager not found"));

        // Convert last_updated from String to LocalDateTime
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //LocalDate lastUpdated = LocalDate.parse(dto.getLast_updated(), formatter);

        // Map DTO to Entity
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity((int) dto.getQuantity());
        inventory.setLastUpdated(dto.getLast_updated());
        inventory.setInventoryManager(manager);

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

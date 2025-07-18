package com.service;

import com.dto.Inventory_AlertDTO;
import com.entity.Inventory_alert;
import com.entity.Product;
import com.entity.User;
import com.repository.InventoryAlertRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryAlertServiceImpl implements InventoryAlertService {

    @Autowired
    private InventoryAlertRepository alertRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Inventory_alert createInventoryAlert(Inventory_AlertDTO dto) {
        // Fetch associated Product
        Product product = productRepo.findById(dto.getProduct_id())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Fetch associated User
        User manager = userRepo.findById(dto.getInventory_manager_id())
                .orElseThrow(() -> new RuntimeException("Inventory Manager not found"));

        // Map DTO to Entity
        Inventory_alert alert = new Inventory_alert();
        alert.setProduct(product);
        alert.setAlert_type(dto.getAlert_type());
        alert.setTrigger_date(dto.getTrigger_date());
        alert.setInventoryManager(manager);

        return alertRepo.save(alert);
    }

    @Override
    public List<Inventory_alert> getAllInventoryAlerts() {
        return alertRepo.findAll();
    }

    @Override
    public Optional<Inventory_alert> getInventoryAlertById(Long id) {
        return alertRepo.findById(id);
    }
}

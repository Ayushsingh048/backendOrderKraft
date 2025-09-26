package com.controller;

import com.entity.Order;
import com.entity.InventoryRawMaterial;
import com.repository.OrderRepository;
import com.repository.InventoryRawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRawMaterialRepository inventoryRepo;

    // GET /reports/recent-orders?limit=10
    @GetMapping("/recent-orders")
    public ResponseEntity<List<Order>> getRecentOrders(@RequestParam(defaultValue = "10") int limit) {
        Pageable page = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "orderDate"));
        Page<Order> ordersPage = orderRepository.findAll(page);
        return ResponseEntity.ok(ordersPage.getContent());
    }

    // GET /reports/stock-level
    @GetMapping("/stock-level")
    public ResponseEntity<List<InventoryRawMaterial>> getStockLevel() {
        return ResponseEntity.ok(inventoryRepo.findAll());
    }
}

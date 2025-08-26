package com.controller;

import com.dto.OrderDTO;
import com.entity.Order;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create new order
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    //get all orders
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
 // Search by ID
    @GetMapping("/search/orderId/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Search by Date
    @GetMapping("/search/orderDate/{date}")
    public List<Order> getOrdersByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return orderService.getOrdersByDate(date);
    }

    // Search by Status
    @GetMapping("/search/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }

    // Search by Total Amount
    @GetMapping("/search/totalAmount/{amount}")
    public List<Order> getOrdersByTotalAmount(@PathVariable Long amount) {
        return orderService.getOrdersByTotalAmount(amount);
    }

    // Search by Procurement Officer ID
    @GetMapping("/search/procurementOfficer/{officerId}")
    public List<Order> getOrdersByProcurementOfficer(@PathVariable Long officerId) {
        return orderService.getOrdersByProcurementOfficer(officerId);
    }

}


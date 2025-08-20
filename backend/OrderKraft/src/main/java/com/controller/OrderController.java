package com.controller;

import com.dto.OrderDTO;
import com.entity.Order;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    
    @GetMapping("/status/{id}")
    public ResponseEntity<?> getOrderStatus(@PathVariable Long id) {
    	String status;
    	try {
    	status = orderService.getOrderStatus(id);
        }
        catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Status Unavailable");
        }
    }
}


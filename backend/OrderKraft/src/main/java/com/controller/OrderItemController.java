package com.controller;

import com.dto.Order_ItemDTO;
import com.entity.OrderItem;
import com.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/order_items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/add")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody Order_ItemDTO dto) {
        return ResponseEntity.ok(orderItemService.createOrderItem(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
    
    //search endpoints 
    @GetMapping("/search/quantity/{quantity}")
    public ResponseEntity<List<OrderItem>> getByQuantity(@PathVariable Long quantity) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByQuantity(quantity));
    }

    @GetMapping("/search/unitPrice/{unitPrice}")
    public ResponseEntity<List<OrderItem>> getByUnitPrice(@PathVariable Double unitPrice) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByUnitPrice(unitPrice));
    }

    @GetMapping("/search/orderId/{orderId}")
    public ResponseEntity<List<OrderItem>> getByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByOrderId(orderId));
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<OrderItem>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByName(name));
    }
}

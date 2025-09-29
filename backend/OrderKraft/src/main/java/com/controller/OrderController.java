package com.controller;

import com.dto.OrderDTO;
import com.dto.UpdateOrderDTO;
import com.entity.Order;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
//@CrossOrigin(origins = "*") // Allow frontend apps to access API
public class OrderController {

    @Autowired
    private OrderService orderService;

    //create new order
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
    	System.out.println(orderDTO.getOrder_date());
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }
    // Get ALL orders
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    //get all orders
    @GetMapping("/all/{id}")
    public ResponseEntity<List<Order>> getAllOrdersByProcurementOfficer(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersByProcurementOfficer(id));
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
    
    //search by order name 
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Order>> getOrdersByName(@PathVariable String name) {
        List<Order> orders = orderService.getOrdersByName(name);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }
    //search by supplier id 
    @GetMapping("/search/supplier/{supplierId}")
    public List<Order> getOrdersBySupplier(@PathVariable Long supplierId) {
        return orderService.getOrdersBySupplierId(supplierId);
    }
//search by delivery date 
    @GetMapping("/search/deliveryDate/{date}")
    public List<Order> getOrdersByDeliveryDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return orderService.getOrdersByDeliveryDate(date);
    }

    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestBody UpdateOrderDTO updateOrderDTO) {
        Order updatedOrder = orderService.UpdateOrderById(updateOrderDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
    	Order order=orderService.updateOrderStatusToCancelled(id);
        return  ResponseEntity.ok(order);
      
    }
    
    @GetMapping("/totalOrders")
    public ResponseEntity<Long> totalOrders(){
    	return ResponseEntity.ok(orderService.getTotalOrders());
    }
    
    @GetMapping("/pendingOrders")
    public ResponseEntity<Long> pendingOrders(){
    	return ResponseEntity.ok(orderService.getPendingOrders());
    }
}


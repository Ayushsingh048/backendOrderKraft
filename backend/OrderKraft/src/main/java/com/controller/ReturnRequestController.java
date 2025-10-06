package com.controller;

import com.dto.ReturnRequestDTO;
import com.entity.ReturnRequest;
import com.service.ReturnRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
@CrossOrigin(origins = "http://localhost:4200") // adjust origin if needed
=======
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

@CrossOrigin(origins = "*") // allow frontend apps (React/Angular) to call this API
>>>>>>> e22bb1e2da40ccbcf98b9dd0d50c5340ff3cea79
@RestController
@RequestMapping("/api/returns")
public class ReturnRequestController {

    @Autowired
    private ReturnRequestServiceImpl service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReturnRequestDTO dto) {
        try {
            ReturnRequest created = service.createReturnRequest(dto);
            return ResponseEntity.status(201).body(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<List<ReturnRequest>> getAll() {
        return ResponseEntity.ok(service.getAll()); // service method fetches all return requests
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ReturnRequest>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.getByOrder(orderId));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ReturnRequest>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(service.getBySupplier(supplierId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String status = body.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "status is required"));
        }

        try {
            ReturnRequest updated = service.updateStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }
    
 // Accept by order id (convenience endpoint for frontend)
    @PutMapping("/order/{orderId}/accept")
    public ResponseEntity<?> acceptByOrder(@PathVariable Long orderId) {
        try {
            ReturnRequest rr = service.acceptByOrderId(orderId);
            return ResponseEntity.ok(rr);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

}

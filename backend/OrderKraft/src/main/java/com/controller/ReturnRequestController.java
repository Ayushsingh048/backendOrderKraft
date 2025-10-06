package com.controller;

import com.dto.ReturnRequestDTO;
import com.entity.ReturnRequest;
import com.service.ReturnRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200") // adjust origin if needed
@RestController
@RequestMapping("/api/returns")
public class ReturnRequestController {

    @Autowired
    private ReturnRequestService service;

    // Create a new return request
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReturnRequestDTO dto) {
        try {
            ReturnRequest created = service.createReturnRequest(dto);
            return ResponseEntity.status(201).body(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }
    
    
//    @GetMapping("/all")
//    public ResponseEntity<List<ReturnRequest>> getAll() {
//        return ResponseEntity.ok(service.getAll()); // service method fetches all return requests
//    }

    // Get all return requests for a specific order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ReturnRequest>> getByOrder(@PathVariable Long orderId) {
        List<ReturnRequest> requests = service.getByOrder(orderId);
        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requests);
    }

    // Update return request status (ACCEPTED / REJECTED)
    @PatchMapping("/{id}/status")
    public ResponseEntity<ReturnRequest> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String status = body.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ReturnRequest updated = service.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}

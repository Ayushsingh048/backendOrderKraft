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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/returns")
public class ReturnRequestController {

    @Autowired
    private ReturnRequestService service;

    // Create a return request (frontend already handles 7-day popup if backend responds with 400)
    @PostMapping
    public ResponseEntity<?> createReturn(@RequestBody ReturnRequestDTO dto) {
        try {
            ReturnRequest saved = service.createReturnRequest(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        }
    }

    // Get all returns (admin)
    @GetMapping("/all")
    public ResponseEntity<List<ReturnRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Get returns for a specific order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ReturnRequest>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.getByOrder(orderId));
    }

    // Get returns for a specific supplier
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ReturnRequest>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(service.getBySupplier(supplierId));
    }

    // Update status (body: { "status": "SUPPLIER_ACCEPTED" } or "ACCEPTED" / "REJECTED")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "status is required"));
        }
        try {
            ReturnRequest updated = service.updateStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        }
    }
}




//package com.controller;
//
//import com.dto.ReturnRequestDTO;
//import com.entity.ReturnRequest;
//import com.service.ReturnRequestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@CrossOrigin(origins = "http://localhost:4200") // adjust origin if needed
//@RestController
//@RequestMapping("/api/returns")
//public class ReturnRequestController {
//
//    @Autowired
//    private ReturnRequestService service;
//
//    // Create a new return request
//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody ReturnRequestDTO dto) {
//        try {
//            ReturnRequest created = service.createReturnRequest(dto);
//            return ResponseEntity.status(201).body(created);
//        } catch (RuntimeException ex) {
//            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
//        }
//    }
//    
//    
////    @GetMapping("/all")
////    public ResponseEntity<List<ReturnRequest>> getAll() {
////        return ResponseEntity.ok(service.getAll()); // service method fetches all return requests
////    }
//
//    // Get all return requests for a specific order
//    @GetMapping("/order/{orderId}")
//    public ResponseEntity<List<ReturnRequest>> getByOrder(@PathVariable Long orderId) {
//        List<ReturnRequest> requests = service.getByOrder(orderId);
//        if (requests.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(requests);
//    }
//
//    // Update return request status (ACCEPTED / REJECTED)
//    @PatchMapping("/{id}/status")
//    public ResponseEntity<ReturnRequest> updateStatus(
//            @PathVariable Long id,
//            @RequestBody Map<String, String> body) {
//
//        String status = body.get("status");
//        if (status == null || status.trim().isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        ReturnRequest updated = service.updateStatus(id, status);
//        return ResponseEntity.ok(updated);
//    }
//}

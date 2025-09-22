package com.controller;

import com.dto.ReturnRequestDTO;
import com.entity.ReturnRequest;
import com.service.ReturnRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class ReturnRequestController {

    @Autowired
    private ReturnRequestService service;

    // Create return request
    @PostMapping
    public ReturnRequest create(@RequestBody ReturnRequestDTO dto) {
        return service.createReturnRequest(dto);
    }

    // Get all return requests for an order
    @GetMapping("/order/{orderId}")
    public List<ReturnRequest> getByOrder(@PathVariable Long orderId) {
        return service.getByOrder(orderId);
    }

    // Update status (ACCEPTED / REJECTED)
    @PatchMapping("/{id}/status")
    public ReturnRequest updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}

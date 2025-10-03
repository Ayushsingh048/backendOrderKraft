package com.controller;

import com.dto.OrderFeedbackRequest;
import com.entity.OrderFeedback;
import com.service.OrderFeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
//@CrossOrigin(origins = "*")
public class OrderFeedbackController {

    private final OrderFeedbackService service;

    public OrderFeedbackController(OrderFeedbackService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderFeedback> saveFeedback(@Valid @RequestBody OrderFeedbackRequest request) {
        return ResponseEntity.ok(service.saveFeedback(request));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderFeedback>> getFeedbackByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.getFeedbackByOrder(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderFeedback>> getAllFeedback() {
        return ResponseEntity.ok(service.getAllFeedback());
    }
}

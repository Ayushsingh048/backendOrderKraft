package com.controller;

import com.dto.PaymentDTO;
import com.entity.Payment;
import com.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;
	
	 @PostMapping("/add")
	    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO) {
	        return ResponseEntity.ok(paymentService.createPayment(paymentDTO));
	    }

	    @GetMapping("/all")
	    public ResponseEntity<List<Payment>> getAllPayments() {
	        return ResponseEntity.ok(paymentService.getAllPayments());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Payment> getPaymentsById(@PathVariable Long payment_id) {
	        return paymentService.getPaymentById(payment_id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }
	    
	    @GetMapping("/search/status/{status}")
	    public ResponseEntity<List<Payment>> getPaymentByStatus(@PathVariable String status) {
	        return ResponseEntity.ok(paymentService.getPaymentByStatus( status));
	    }

	    @GetMapping("/search/session/{method}")
	    public ResponseEntity<List<Payment>> getPaymentByMethod(@PathVariable String method) {
	        return ResponseEntity.ok(paymentService.getPaymentByMethod(method));
	    }
	    
}

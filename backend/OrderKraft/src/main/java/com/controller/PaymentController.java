package com.controller;

import com.dto.PaymentDTO;
import com.dto.ProductRequest;
import com.dto.StripeResponse;
import com.entity.Payment;

import com.service.PaymentService;
import com.service.StripeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;
	@Autowired
	private StripeService stripeService;
	
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
	    
	    @PostMapping("/checkout")
	    public StripeResponse checkoutProducts(@RequestBody ProductRequest productRequest) {
	    	System.out.println(productRequest);
	        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
	        return stripeResponse;
	    }
	    @GetMapping("/success")
	    public ResponseEntity<?> paymentSuccess(@RequestParam("session_id") String sessionId) {
	        return ResponseEntity.ok(stripeService.checkPaymentStatus(sessionId));
	    }
	    @GetMapping("/check")
	    public ResponseEntity<?> paymentCheck(@RequestParam String orderid) {
	        return ResponseEntity.ok(paymentService.fetchStatus(orderid));
	    }
	    
}

package com.controller;

import com.dto.CheckoutRequest;
import com.dto.PaymentDTO;
import com.dto.ProductRequest;
import com.dto.StripeResponse;
import com.entity.Payment;
import com.repository.PaymentRepository;
import com.service.PaymentService;
import com.service.StripeService;
import com.stripe.exception.StripeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;
	@Autowired
	private StripeService stripeService;
	@Autowired
	private PaymentRepository payrepo;
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
	    
	    //stripe service payment status
	    @GetMapping("/check")
	    public ResponseEntity<?> paymentCheck(@RequestParam String orderid) {
	        return ResponseEntity.ok(paymentService.fetchStatus(orderid));
	    }
	    
//	    //payment status from database
//	    @GetMapping("/status/{orderId}")
//	    public ResponseEntity<?> paymentStatusByorderId(@PathVariable String orderId){
//	    	Payment payment = paymentService.getPaymentByorder_id(orderId);
//	    	Map<String, String> response = new HashMap<>();
//	    	response.put("status", "succeeded");
//	    	return ResponseEntity.ok(response);
//	    }
//	    
	    
	    //refund for the payment using order id
	    @PostMapping("/refund/{orderId}")
	    public String refundPayment(@PathVariable String orderId) throws Exception {
	    	//get payment table by order id
	    	Payment payment = paymentService.getPaymentByorder_id(orderId);
	    	String sessionId = payment.getSession_id();
	    	stripeService.refundPaymentBySession(sessionId);
	    	payment.setStatus("Refunded");
	    	payrepo.save(payment);
	    	return "refunded!!!";
	    }
	    
	    
	    
	    //payment to a already connected account returns session url to complete payment on portal
	    @PostMapping("/checkout-connected-account")
	    public Map<String, String> createCheckout(@RequestBody CheckoutRequest request) throws StripeException {
	        return stripeService.createCheckoutSession(
	                request.getAmountInCents(),
	                request.getCurrency(),
	                request.getConnectedAccountId(),
	                request.getSuccessUrl(),
	                request.getCancelUrl(),
	                request.getOrderName()
	        );
	    }
}

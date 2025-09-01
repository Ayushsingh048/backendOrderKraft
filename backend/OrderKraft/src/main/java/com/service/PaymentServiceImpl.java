package com.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.PaymentDTO;

import com.entity.Order;
import com.entity.Payment;

import com.repository.OrderRepository;
import com.repository.PaymentRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	
	 @Autowired
	    private PaymentRepository paymentRepo;

	    @Autowired
	    private OrderRepository orderRepo;
	    
	    @Autowired
	    private StripeService stripeService;

	    @Override
	    public Payment createPayment(PaymentDTO dto) {
	        Order order = orderRepo.findById(dto.getOrder_id())
	                            .orElseThrow(() -> new RuntimeException("Order not found"));
	        
	        Payment payment = new Payment();
	        payment.setPaymentId(dto.getPayment_id());
	        payment.setAmount(dto.getAmount());
	        payment.setStatus(dto.getStatus());
	        payment.setMethod(dto.getMethod());
	        payment.setPaymentDate(LocalDateTime.now());
	        payment.setOrder(order);
	        payment.setSession_id(dto.getSession_id());

	        return paymentRepo.save(payment);
	    }
	

//	@Override
//	public Payment createPayment(PaymentDTO dto) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Payment> getAllPayments() {
		// TODO Auto-generated method stub
		return paymentRepo.findAll();
	}

	@Override
	public Optional<Payment> getPaymentById(Long id) {
		// TODO Auto-generated method stub
		return paymentRepo.findById(id);
	}

	@Override
	public List<Payment> getPaymentByDate(LocalDate payment_date) {
		// TODO Auto-generated method stub
		return paymentRepo.findByPaymentDate(payment_date);
	}

	@Override
	public List<Payment> getPaymentByStatus(String status) {
		// TODO Auto-generated method stub
		return paymentRepo.findByStatus(status);
	}

	@Override
	public List<Payment> getPaymentByMethod(String method) {
		// TODO Auto-generated method stub
		return paymentRepo.findByMethod(method);
	}


	@Override
	public Payment DeletePaymentByID(Long id) {
		// TODO Auto-generated method stub
		paymentRepo.deleteById(id);
		return null;
	}


	@Override
	public void updateSessionId(String sid, Long id) {
		// TODO Auto-generated method stub

		
	}


	@Override
	public Payment getPaymentByorder_id(String order_id) {
	    Long oid = Long.parseLong(order_id); // Convert String -> Long
	    return (Payment) paymentRepo.findByOrder_OrderId(oid).get(0);
	}
//fetch status from stripe
	@Override
	public
	Map<String, Object> fetchStatus(String order_id)
	{
		Payment payment= this.getPaymentByorder_id(order_id);
		
		Map<String, Object> paymentdetail = stripeService.checkPaymentStatus(payment.getSession_id());
		payment.setStatus(paymentdetail.get("status").toString());
		paymentRepo.save(payment);
		return paymentdetail;
		
	}

}

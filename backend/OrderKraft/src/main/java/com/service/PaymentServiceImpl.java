package com.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.PaymentDTO;

import com.entity.Order;
import com.entity.Payment;

import com.repository.OrderRepository;
import com.repository.PaymentRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	
	 @Autowired
	    private PaymentRepository paymentRepo;

	    @Autowired
	    private OrderRepository orderRepo;

	    @Override
	    public Payment createPayment(PaymentDTO dto) {
	        Order order = orderRepo.findById(dto.getOrder_id())
	                            .orElseThrow(() -> new RuntimeException("Order not found"));

	        Payment payment = new Payment();
	        payment.setPaymentId(dto.getPayment_id());
	        payment.setAmount(dto.getAmount());
	        payment.setStatus(dto.getStatus());
	        payment.setMethod(dto.getMethod());
	        payment.setOrder(order);

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
	public List<Payment> DeletePaymentByID(Long id) {
		// TODO Auto-generated method stub
		paymentRepo.deleteById(id);
		return null;
	}

	


	

}

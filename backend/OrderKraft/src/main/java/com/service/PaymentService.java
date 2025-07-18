package com.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.dto.PaymentDTO;

import com.entity.Payment;

public interface PaymentService {

	 Payment createPayment(PaymentDTO dto);
	    List<Payment> getAllPayments();
	    Optional<Payment> getPaymentById(Long id); 
	    List<Payment> getPaymentByDate(LocalDate payment_date);
	    List<Payment> getPaymentByStatus(String status);
	    List<Payment> getPaymentByMethod(String method);
	    List<Payment> DeletePaymentByID(Long id);
	
}

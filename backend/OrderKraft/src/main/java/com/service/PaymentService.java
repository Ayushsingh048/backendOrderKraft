package com.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
	    Payment getPaymentByorder_id(String order_id);
	    List<Payment> DeletePaymentByID(Long id);
	    void updateSessionId(String sid,Long id);
		Map<String, Object> fetchStatus(String order_id);
	
}

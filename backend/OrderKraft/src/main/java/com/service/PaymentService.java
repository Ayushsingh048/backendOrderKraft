package com.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dto.PaymentDTO;

import com.entity.Payment;

public interface PaymentService {
		
		//inserting a new row in the payment table
	 	Payment createPayment(PaymentDTO dto);
	 	//returns all the payments
	    List<Payment> getAllPayments();
	    //returns payment object by id
	    Optional<Payment> getPaymentById(Long id); 
	    //returns payment object by Date
	    List<Payment> getPaymentByDate(LocalDate payment_date);
	    //returns payment object by Status
	    List<Payment> getPaymentByStatus(String status);
	    //returns payment object by Method/mode of payment
	    List<Payment> getPaymentByMethod(String method);
	    //returns payment object of particular Order ID
	    Payment getPaymentByorder_id(String order_id);
	    //For deleting a payment entry by using Payment ID
	    Payment DeletePaymentByID(Long id);
	    //Update sessionId column in payment
	    void updateSessionId(String sid,Long id);
	    //get stripe payment status
		Map<String, Object> fetchStatus(String order_id);
//		//returns payment status using Order ID
//		String getPaymentStatusByOrderId(Long orderId);
	
}

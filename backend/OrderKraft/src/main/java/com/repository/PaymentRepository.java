package com.repository;

import java.time.LocalDate;
import java.util.*;
import com.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository <Payment, Long> {

	 //Optional<Payment> findByPaymentId(long paymentId);
	    List<Payment> findByStatus(String status);
	    List<Payment> findByPaymentDate(LocalDate paymentDate);
	    List<Payment> findByMethod(String method);
//	    void deletePaymentById(Long id);
}

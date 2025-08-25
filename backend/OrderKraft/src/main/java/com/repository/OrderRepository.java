package com.repository;

import com.entity.Order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByOrderDate(LocalDate orderDate);
    List<Order> findByStatus(String status);
    List<Order> findByTotalAmount(Long totalAmount);
    List<Order> findByProcurementOfficer_UserId(Long userId);
}

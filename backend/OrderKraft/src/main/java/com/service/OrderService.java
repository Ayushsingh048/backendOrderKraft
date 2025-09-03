package com.service;

import com.dto.OrderDTO;
import com.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    List<Order> getAllOrders();
	Order getOrderById(Long id);
	String getOrderStatus(Long orderId);
    
 // search methods

    List<Order> getOrdersByDate(LocalDate orderDate);
    List<Order> getOrdersByStatus(String status);
    List<Order> getOrdersByTotalAmount(Long totalAmount);
    List<Order> getOrdersByProcurementOfficer(Long officerId);
	List<Order> getOrdersByName(String name);
}

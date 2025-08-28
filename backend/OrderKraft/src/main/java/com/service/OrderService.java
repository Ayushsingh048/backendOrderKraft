package com.service;

import com.dto.OrderDTO;
import com.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    List<Order> getAllOrders();
<<<<<<< HEAD
	Order getOrderById(Long id);
	String getOrderStatus(Long orderId);
=======
    Order getOrderById(Long id);
    
 // search methods

    List<Order> getOrdersByDate(LocalDate orderDate);
    List<Order> getOrdersByStatus(String status);
    List<Order> getOrdersByTotalAmount(Long totalAmount);
    List<Order> getOrdersByProcurementOfficer(Long officerId);
>>>>>>> f7c71e932f217c70caa9f1c7845a6bd4f7450db9
}

package com.service;

import com.dto.OrderDTO;
import com.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    List<Order> getAllOrders();
	Order getOrderById(Long id);
	String getOrderStatus(Long orderId);
}

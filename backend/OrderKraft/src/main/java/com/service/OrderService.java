package com.service;

import com.dto.OrderDTO;
import com.dto.UpdateOrderDTO;
import com.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);
    List<Order> getAllOrders();
	Order getOrderById(Long id);
	String getOrderStatus(Long orderId);
	//cancel order
	Order updateOrderStatusToCancelled(Long orderId);
	//completed order
	Order updateOrderStatusToCompleted(Long orderId);
	
 // search methods

    List<Order> getOrdersByDate(LocalDate orderDate);
    List<Order> getOrdersByStatus(String status);
    List<Order> getOrdersByTotalAmount(Long totalAmount);
    List<Order> getOrdersByProcurementOfficer(Long officerId);
	List<Order> getOrdersByName(String name);
	Order UpdateOrderById(UpdateOrderDTO updateOrderDTO);
    List<Order> getOrdersBySupplierId(Long supplierId);
    List<Order> getOrdersByDeliveryDate(LocalDate deliveryDate);
//    public List<SupplierOrderSummary> getSupplierOrderSummary();
	Long getTotalOrders();
	Long getPendingOrders();
}

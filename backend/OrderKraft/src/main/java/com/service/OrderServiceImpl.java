package com.service;

import com.dto.OrderDTO;
import com.entity.Order;
import com.entity.User;
import com.repository.OrderRepository;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Order createOrder(OrderDTO dto) {
        Order order = new Order();
        order.setOrderDate(dto.getOrder_date());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotal_amount());
        order.setOrderName(dto.getOrder_name());

        Optional<User> officer = userRepo.findById(dto.getProcurement_officer_id());
                        
        order.setProcurementOfficer(officer.get());

        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }


    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        return order;
    }

	@Override
	public String getOrderStatus(Long id) {
		Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid order ID"));
		String status = order.getStatus();
		return status;
	}

    @Override
    public List<Order> getOrdersByDate(LocalDate orderDate) {
        return orderRepo.findByOrderDate(orderDate);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepo.findByStatus(status);
    }

    @Override
    public List<Order> getOrdersByTotalAmount(Long totalAmount) {
        return orderRepo.findByTotalAmount(totalAmount);
    }

    @Override
    public List<Order> getOrdersByProcurementOfficer(Long officerId) {
    	return orderRepo.findByProcurementOfficer_Id(officerId);

    }
    @Override
    public List<Order> getOrdersByName(String name) {
        return orderRepo.findByOrderNameContainingIgnoreCase(name);
    }
    
    
    //cancel order
    @Override
    public Order updateOrderStatusToCancelled(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setStatus("cancelled");
        return orderRepo.save(order);
    }

    

}

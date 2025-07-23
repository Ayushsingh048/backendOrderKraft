package com.service;

import com.dto.OrderDTO;
import com.entity.Order;
import com.entity.User;
import com.repository.OrderRepository;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Optional<User> officer = userRepo.findById(dto.getProcurement_officer_id());
                        
        order.setProcurementOfficer(officer.get());

        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

//    @Override
//    public Order getOrderById(Long id) {
//        Order order = orderRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
//        return convertToDTO(order);
//    }

}

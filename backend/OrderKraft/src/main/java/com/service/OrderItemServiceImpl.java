package com.service;

import com.dto.Order_ItemDTO;
import com.entity.Order;
import com.entity.OrderItem;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderItem createOrderItem(Order_ItemDTO dto) {
        Order order = orderRepository.findById(dto.getOrder_id())
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + dto.getOrder_id()));

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUnitPrice(dto.getUnit_price());
        orderItem.setOrder(order);

        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + id));
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
    
    @Override
    public List<OrderItem> getOrderItemsByQuantity(Long quantity) {
        return orderItemRepository.findByQuantity(quantity);
    }

    @Override
    public List<OrderItem> getOrderItemsByUnitPrice(Double unitPrice) {
        return orderItemRepository.findByUnitPrice(unitPrice);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrder_OrderId(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemsByName(String name) {
        return orderItemRepository.findByName(name);
    }
}

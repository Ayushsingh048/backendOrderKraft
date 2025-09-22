package com.repository;

import com.entity.OrderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByQuantity(Long quantity);
    List<OrderItem> findByUnitPrice(Double unitPrice);
    List<OrderItem> findByOrder_OrderId(Long orderId);
    List<OrderItem> findByName(String name);
}

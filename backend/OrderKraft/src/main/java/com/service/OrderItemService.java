package com.service;

import com.dto.Order_ItemDTO;
import com.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(Order_ItemDTO dto);
    List<OrderItem> getAllOrderItems();
    OrderItem getOrderItemById(Long id);
    void deleteOrderItem(Long id);
    
 // search methods
    List<OrderItem> getOrderItemsByQuantity(Long quantity);
    List<OrderItem> getOrderItemsByUnitPrice(Double unitPrice);
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    List<OrderItem> getOrderItemsByName(String name);
}

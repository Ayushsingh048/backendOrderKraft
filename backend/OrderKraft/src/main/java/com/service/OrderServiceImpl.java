package com.service;

import com.dto.OrderDTO;
import com.dto.UpdateOrderDTO;
import com.entity.Order;
import com.entity.OrderItem;
import com.entity.User;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private OrderItemRepository orderItemRepo;

    @Override
    public Order createOrder(OrderDTO dto) {
        Order order = new Order();
        order.setOrderDate(dto.getOrder_date());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotal_amount());
        order.setOrderName(dto.getOrder_name());
        order.setDeliveryDate(dto.getDelivery_date());
        order.setSupplierId(dto.getSupplier_id());

        // ✅ Safely fetch Procurement Officer
        User officer = userRepo.findById(dto.getProcurement_officer_id())
                .orElseThrow(() -> new RuntimeException(
                    "Procurement Officer not found with ID: " + dto.getProcurement_officer_id()
                ));
        order.setProcurementOfficer(officer);

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

    @Override
    public Order UpdateOrderById(UpdateOrderDTO updateOrderDTO) {
        // 1. Fetch the order
        Order order = orderRepo.findById(updateOrderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + updateOrderDTO.getOrderId()));

     // 2. Update order name if provided
        if (updateOrderDTO.getName() != null) {
            order.setOrderName(updateOrderDTO.getName());
        }

     // 3. Update specific order item if provided
        if (updateOrderDTO.getOrderItemId() != null && updateOrderDTO.getQuantity() != null) {
            OrderItem orderItem = orderItemRepo.findById(updateOrderDTO.getOrderItemId())
                    .orElseThrow(() -> new RuntimeException("Order Item not found with ID: " + updateOrderDTO.getOrderItemId()));

            if (!Objects.equals(orderItem.getOrder().getOrderId(), updateOrderDTO.getOrderId())) {
                throw new RuntimeException("Order item does not belong to the given order");
            }


            // Prevent negative quantities
            if (updateOrderDTO.getQuantity() < 0) {
                throw new RuntimeException("Quantity cannot be negative");
            }

            // Update quantity
            orderItem.setQuantity(updateOrderDTO.getQuantity());
            orderItemRepo.save(orderItem);

            // ✅ Force status to "REQUESTED" after any quantity update
            order.setStatus("REQUESTED");
        }
        // 4. Recalculate total amount from all items
        List<OrderItem> allItems = orderItemRepo.findByOrder_OrderId(updateOrderDTO.getOrderId());
        BigDecimal total = allItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        // 5. Save updated order
        return orderRepo.save(order);
    }
    @Override
    public List<Order> getOrdersBySupplierId(Long supplierId) {
        return orderRepo.findBySupplierId(supplierId);
    }
    

    @Override
    public List<Order> getOrdersByDeliveryDate(LocalDate deliveryDate) {
        return orderRepo.findByDeliveryDate(deliveryDate);
    }
    
    
    //cancel order
    @Override
    public Order updateOrderStatusToCancelled(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setStatus("cancelled");
        return orderRepo.save(order);
    }

	@Override
	public Long getTotalOrders() {
		return orderRepo.count();
	}

	@Override
	public Long getPendingOrders() {
		return orderRepo.count() - (orderRepo.countByStatus("Received") + orderRepo.countByStatus("Cancelled"));
	}
    
  //completed order - updating status to completed
    @Override
    public Order updateOrderStatusToCompleted(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setStatus("Completed");
        return orderRepo.save(order);
    }
    @Override
    public Order updateOrderStatusToReceived(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setStatus("received");
        return orderRepo.save(order);
    }
    
    

}

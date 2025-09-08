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
import java.util.Optional;

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
        System.out.println("test suplier id"+dto.getSupplier_id());
        order.setSupplierId(dto.getSupplier_id());

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

	@Override
	public Order UpdateOrderById(UpdateOrderDTO updateOrderDTO) {
	    // 1. Fetch the order
	    Order order = orderRepo.findById((long) updateOrderDTO.getID())
	            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + updateOrderDTO.getID()));

	    // 2. Update order fields
	    if (updateOrderDTO.getName() != null) {
	        order.setOrderName(updateOrderDTO.getName());
	    }
//	    if (updateOrderDTO.getTotalAmount() != null) {
//	        order.setTotalAmount(updateOrderDTO.getTotalAmount());
//	    }

	    // 3. Update related order item quantity
	   List <OrderItem> orderItem =  orderItemRepo.findByOrder_OrderId((long) updateOrderDTO.getID());
	    if (orderItem != null && updateOrderDTO.getQuantity() != null) {
	        orderItem.get(0).setQuantity(updateOrderDTO.getQuantity());
	        orderItemRepo.save(orderItem.get(0));
	    }
	   
	    order.setTotalAmount(orderItem.get(0).getUnitPrice().multiply(BigDecimal.valueOf(orderItem.get(0).getQuantity())));
	    // 4. Save updated order
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
    

}

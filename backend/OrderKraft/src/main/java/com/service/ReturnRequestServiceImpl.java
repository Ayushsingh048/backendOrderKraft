package com.service;

import com.dto.ReturnRequestDTO;
import com.entity.Order;
import com.entity.ReturnRequest;
import com.entity.Inventory;
import com.repository.OrderRepository;
import com.repository.ReturnRequestRepository;
import com.repository.InventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {

    @Autowired
    private ReturnRequestRepository repo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private InventoryRepository inventoryRepo;

    @Override
    @Transactional
    public ReturnRequest createReturnRequest(ReturnRequestDTO dto) {
        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // validate 7-day return window
        long daysSinceDelivery = ChronoUnit.DAYS.between(order.getDeliveryDate(), LocalDate.now());
        if (daysSinceDelivery > 7) {
            throw new RuntimeException("Return window closed");
        }

        ReturnRequest req = new ReturnRequest();
        req.setOrderId(order.getOrderId());
        req.setSupplierId(order.getSupplierId());

        // set productId & quantity from DTO (client must provide)
        req.setProductId(dto.getProductId());
        req.setQuantity(dto.getQuantity() == null ? 1 : dto.getQuantity());

        req.setReason(dto.getReason());
        req.setComment(dto.getComment());
        req.setRequestDate(LocalDate.now());
        req.setStatus("PENDING");

        return repo.save(req);
    }

    @Override
    public List<ReturnRequest> getByOrder(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public ReturnRequest updateStatus(Long id, String status) {
        ReturnRequest req = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        String normalized = status == null ? null : status.toUpperCase();
        String oldStatus = req.getStatus();
        req.setStatus(normalized);
        ReturnRequest saved = repo.save(req);

        // Only perform decrement when transitioning to ACCEPTED from a non-ACCEPTED state
        if (!"ACCEPTED".equalsIgnoreCase(oldStatus) && "ACCEPTED".equalsIgnoreCase(normalized)) {
            Long productId = saved.getProductId();
            Integer qty = saved.getQuantity();
            if (productId == null) {
                throw new RuntimeException("Return request missing productId; cannot update inventory");
            }
            int returnQty = (qty == null || qty <= 0) ? 1 : qty;

            Inventory inventory = inventoryRepo.findByProduct_ProductId(productId)
                    .orElseThrow(() -> new RuntimeException("Inventory not found for product id: " + productId));

            int updatedQty = inventory.getQuantity() - returnQty;
            if (updatedQty < 0) updatedQty = 0;

            inventory.setQuantity(updatedQty);
            inventory.setLastUpdated(LocalDate.now());
            inventoryRepo.save(inventory);
        }

        return saved;
    }
}

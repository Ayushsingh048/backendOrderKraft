package com.service;

import com.dto.ReturnRequestDTO;
import com.entity.Order;
import com.entity.ReturnRequest;
import com.repository.OrderRepository;
import com.repository.ReturnRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {

    @Autowired
    private ReturnRequestRepository repo;

    @Autowired
    private OrderRepository orderRepo;

    @Override
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
    public ReturnRequest updateStatus(Long id, String status) {
        ReturnRequest req = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return request not found"));
        req.setStatus(status.toUpperCase());
        return repo.save(req);
    }
}

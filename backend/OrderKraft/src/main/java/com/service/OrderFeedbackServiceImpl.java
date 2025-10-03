package com.service;

import com.dto.OrderFeedbackRequest;
import com.entity.OrderFeedback;
import com.repository.OrderFeedbackRepository;
import com.service.OrderFeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFeedbackServiceImpl implements OrderFeedbackService {

    private final OrderFeedbackRepository repo;

    public OrderFeedbackServiceImpl(OrderFeedbackRepository repo) {
        this.repo = repo;
    }

    @Override
    public OrderFeedback saveFeedback(OrderFeedbackRequest req) {
        OrderFeedback f = new OrderFeedback();
        f.setOrderId(req.getOrderId());
        f.setCommunication(req.getCommunication());
        f.setDelivery(req.getDelivery());
        f.setQuality(req.getQuality());
        f.setFeedback(req.getFeedback());
        return repo.save(f);
    }

    @Override
    public List<OrderFeedback> getFeedbackByOrder(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    @Override
    public List<OrderFeedback> getAllFeedback() {
        return repo.findAll();
    }
}

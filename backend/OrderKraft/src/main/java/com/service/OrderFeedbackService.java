package com.service;

import com.dto.OrderFeedbackRequest;
import com.entity.OrderFeedback;

import java.util.List;

public interface OrderFeedbackService {
    OrderFeedback saveFeedback(OrderFeedbackRequest request);
    List<OrderFeedback> getFeedbackByOrder(Long orderId);
    List<OrderFeedback> getAllFeedback();
}

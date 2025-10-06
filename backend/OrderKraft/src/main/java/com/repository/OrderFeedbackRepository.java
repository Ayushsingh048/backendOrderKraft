package com.repository;

import com.entity.OrderFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderFeedbackRepository extends JpaRepository<OrderFeedback, Long> {
    List<OrderFeedback> findByOrderId(Long orderId);
}

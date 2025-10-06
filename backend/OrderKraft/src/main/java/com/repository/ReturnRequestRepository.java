package com.repository;

import com.entity.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
    List<ReturnRequest> findByOrderId(Long orderId);
    List<ReturnRequest> findBySupplierId(Long supplierId);
    List<ReturnRequest> findByStatus(String status);
 // add this method to your repository interface
    Optional<ReturnRequest> findFirstByOrderIdOrderByIdDesc(Long orderId);

}

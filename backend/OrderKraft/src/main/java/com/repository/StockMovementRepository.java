package com.repository;

import com.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductId(Long productId);
    List<StockMovement> findByMovementDate(LocalDate date);  // ✅ renamed
    List<StockMovement> findByProductIdAndMovementDateBetween(Long productId, LocalDate start, LocalDate end); // ✅ renamed
}

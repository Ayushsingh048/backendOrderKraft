package com.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.ProductionUnit;

public interface ProductionUnitRepository extends JpaRepository<ProductionUnit, Long> {

    // Find a single unit by its name
    Optional<ProductionUnit> findByName(String name);

    // Find all units with the given capacity
    List<ProductionUnit> findByCapacity(long capacity);

    // Find all units associated with a specific task ID
    List<ProductionUnit> findByTaskId(Long taskId);

    // Find all units managed by a specific production manager
    List<ProductionUnit> findByProductionManagerId(Long productionManagerId);
}

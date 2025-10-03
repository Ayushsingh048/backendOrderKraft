package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ProductionUnit;

public interface ProductionUnitRepository extends JpaRepository<ProductionUnit, Long> {


    // Find unit by name
    Optional<ProductionUnit> findByName(String name);
}

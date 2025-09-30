package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.Production_UnitDTO;
import com.entity.ProductionUnit;

public interface ProductionUnitService {

    // Create and save a new production unit
    ProductionUnit createProductionUnit(Production_UnitDTO dto);

    // Get all production units
    List<ProductionUnit> getAllProductionUnits();

    // Get unit by its ID
    Optional<ProductionUnit> getProductionUnitById(Long id);

    // Get unit by name
    Optional<ProductionUnit> getProductionUnitByName(String name);
}

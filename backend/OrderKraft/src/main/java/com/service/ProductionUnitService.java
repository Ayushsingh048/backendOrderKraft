package com.service;

import java.util.List;
import java.util.Optional;

import com.dto.Production_UnitDTO;
import com.entity.ProductionUnit;

public interface ProductionUnitService {

    // Create and save a new production unit
    ProductionUnit createProductionUnit(Production_UnitDTO dto);

    // Retrieve all production units
    List<ProductionUnit> getAllProductionUnits();

    // Retrieve a unit by its ID
    Optional<ProductionUnit> getProductionUnitById(Long id);

    // Retrieve a unit by its name
    Optional<ProductionUnit> getProductionUnitByName(String name);

    // Retrieve units by capacity
    List<ProductionUnit> getProductionUnitsByCapacity(long capacity);

    // Retrieve units by task ID
    List<ProductionUnit> getProductionUnitsByTaskId(Long taskId);

    // Retrieve units by production manager ID
    List<ProductionUnit> getProductionUnitsByProductionManagerId(Long productionManagerId);
    
    //  Update task ID for a specific unit
    ProductionUnit updateTaskId(Long unitId, Long taskId);
}

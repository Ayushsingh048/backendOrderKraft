package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Production_UnitDTO;
import com.entity.ProductionUnit;
import com.repository.ProductionUnitRepository;

@Service
public class ProductionUnitServiceImpl implements ProductionUnitService {

    @Autowired
    private ProductionUnitRepository productionUnitRepo;
    
   //Create a new ProductionUnit entity from a DTO and save it to the database.
    @Override
    public ProductionUnit createProductionUnit(Production_UnitDTO dto) {
        ProductionUnit unit = new ProductionUnit();
        unit.setName(dto.getName());
        unit.setCapacity(dto.getCapacity());
        unit.setProductionManagerId(dto.getProduction_manager_id());
        unit.setTaskId(dto.getTask_id());
        return productionUnitRepo.save(unit);
    }

     // Get all production units.
    @Override
    public List<ProductionUnit> getAllProductionUnits() {
        return productionUnitRepo.findAll();
    }

    // Find a production unit by ID.
    @Override
    public Optional<ProductionUnit> getProductionUnitById(Long id) {
        return productionUnitRepo.findById(id);
    }

   // Find a production unit by its name.
    @Override
    public Optional<ProductionUnit> getProductionUnitByName(String name) {
        return productionUnitRepo.findByName(name);
    }

   // Find all production units matching the given capacity.
    @Override
    public List<ProductionUnit> getProductionUnitsByCapacity(long capacity) {
        return productionUnitRepo.findByCapacity(capacity);
    }

    // Find all production units that belong to a given task.
    @Override
    public List<ProductionUnit> getProductionUnitsByTaskId(Long taskId) {
        return productionUnitRepo.findByTaskId(taskId);
    }
    
  // Find all production units managed by a specific production manager.
    @Override
    public List<ProductionUnit> getProductionUnitsByProductionManagerId(Long productionManagerId) {
        return productionUnitRepo.findByProductionManagerId(productionManagerId);
    }
    
    //  Update task ID for a specific unit
    @Override
    public ProductionUnit updateTaskId(Long unitId, Long taskId) {
        Optional<ProductionUnit> optionalUnit = productionUnitRepo.findById(unitId);
        if (optionalUnit.isPresent()) {
            ProductionUnit unit = optionalUnit.get();
            unit.setTaskId(taskId);
            return productionUnitRepo.save(unit);
        } else {
            throw new RuntimeException("Production unit not found with id: " + unitId);
        }
    }
}

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


    @Override
    public ProductionUnit createProductionUnit(Production_UnitDTO dto) {
        // Map DTO to Entity
        ProductionUnit unit = new ProductionUnit();
        unit.setName(dto.getName());
        unit.setCapacity((int) dto.getCapacity());

        return productionUnitRepo.save(unit);
    }

    @Override
    public List<ProductionUnit> getAllProductionUnits() {
        return productionUnitRepo.findAll();
    }

    @Override
    public Optional<ProductionUnit> getProductionUnitById(Long id) {
        return productionUnitRepo.findById(id);
    }

    @Override
    public Optional<ProductionUnit> getProductionUnitByName(String name) {
        return productionUnitRepo.findByName(name);
    }
}

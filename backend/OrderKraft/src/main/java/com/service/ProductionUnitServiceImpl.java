package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Production_UnitDTO;
import com.entity.ProductionUnit;
import com.entity.User;
import com.repository.ProductionUnitRepository;
import com.repository.UserRepository;

@Service
public class ProductionUnitServiceImpl implements ProductionUnitService {

    @Autowired
    private ProductionUnitRepository productionUnitRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public ProductionUnit createProductionUnit(Production_UnitDTO dto) {
        // Fetch the production manager (User) by ID
        User manager = userRepo.findById(dto.getProduction_manager_id())
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + dto.getProduction_manager_id()));

        // Map DTO to Entity
        ProductionUnit unit = new ProductionUnit();
        unit.setName(dto.getName());
        unit.setCapacity((int) dto.getCapacity());
        unit.setUser(manager);

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
    public List<ProductionUnit> getUnitsByProductionManagerId(Long productionManagerId) {
        return productionUnitRepo.findByUser_Id(productionManagerId);
    }

    @Override
    public Optional<ProductionUnit> getProductionUnitByName(String name) {
        return productionUnitRepo.findByName(name);
    }
}

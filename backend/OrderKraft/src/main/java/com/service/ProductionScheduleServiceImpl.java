package com.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.Production_ScheduleDTO;
import com.entity.BOM;
import com.entity.BOM_Material;
import com.entity.InventoryRawMaterial;
import com.entity.ProductionSchedule;
import com.repository.BOMRepository;
import com.repository.InventoryRawMaterialRepository;
import com.repository.ProductionScheduleRepository;

@Service
@Transactional
public class ProductionScheduleServiceImpl implements ProductionScheduleService {

    @Autowired
    private BOMRepository bomRepository;

    @Autowired
    private InventoryRawMaterialRepository inventoryRawMaterialRepository;

    @Autowired
    private ProductionScheduleRepository scheduleRepository;

    @Override
    public ProductionSchedule createProductionSchedule(Production_ScheduleDTO dto) {

        // Step 1: Fetch BOM
        BOM bom = bomRepository.findById(dto.getBomId())
                .orElseThrow(() -> new RuntimeException("BOM not found"));

        // Step 2: Check material availability
        for (BOM_Material material : bom.getMaterials()) {
            InventoryRawMaterial raw = material.getRawmaterial();
            long requiredQty = material.getQntperunit() * dto.getQuantityToProduce();

            if (raw.getQuantity() < requiredQty) {
                throw new RuntimeException("Insufficient stock for raw material: " + raw.getName());
            }
        }

        // Step 3: Deduct from inventory
        for (BOM_Material material : bom.getMaterials()) {
            InventoryRawMaterial raw = material.getRawmaterial();
            long requiredQty = material.getQntperunit() * dto.getQuantityToProduce();

            raw.setQuantity(raw.getQuantity() - requiredQty);
            raw.setLastUpdated(LocalDate.now());
            inventoryRawMaterialRepository.save(raw);
        }

        // ✅ Step 4: Create and save production schedule properly
        ProductionSchedule schedule = new ProductionSchedule();
        schedule.setBom(bom); // Mandatory foreign key
        schedule.setQuantityToProduce(dto.getQuantityToProduce()); // Required
        schedule.setStartDate(dto.getStartDate());
        schedule.setEndDate(dto.getEndDate());
        schedule.setStatus("Scheduled");
        schedule.setCreatedOn(LocalDate.now()); // Set created date

        System.out.println(">>> Creating Production Schedule for BOM ID: " + bom.getBom_id());
        System.out.println(">>> Quantity to produce: " + dto.getQuantityToProduce());
        System.out.println(">>> Start Date: " + dto.getStartDate() + ", End Date: " + dto.getEndDate());

        ProductionSchedule savedSchedule = scheduleRepository.save(schedule);
        System.out.println(">>> Saved schedule with ID: " + savedSchedule.getId());

        return savedSchedule;
    }

    @Override
    public List<ProductionSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}

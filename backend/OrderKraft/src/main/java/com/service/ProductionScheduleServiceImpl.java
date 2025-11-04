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
        System.out.println("=== [START] createProductionSchedule() ===");
        System.out.println("Received DTO details:");
        System.out.println("   → BOM ID: " + dto.getBomId());
        System.out.println("   → Quantity to produce: " + dto.getQuantityToProduce());
        System.out.println("   → Start Date: " + dto.getStartDate());
        System.out.println("   → End Date: " + dto.getEndDate());

        // Step 1: Fetch BOM
        System.out.println(">>> Fetching BOM from repository...");
        BOM bom = bomRepository.findById(dto.getBomId())
                .orElseThrow(() -> new RuntimeException("BOM not found for ID: " + dto.getBomId()));
        System.out.println(">>> BOM fetched successfully: " + bom.getBom_id());
        System.out.println(">>> Total materials linked to BOM: " + (bom.getMaterials() != null ? bom.getMaterials().size() : 0));

        // Step 2: Check material availability
        System.out.println(">>> Checking raw material availability...");
        for (BOM_Material material : bom.getMaterials()) {
            InventoryRawMaterial raw = material.getRawmaterial();
            if (raw == null) {
                System.out.println("⚠️  Raw material not linked for material ID: " + material.getMaterialId());
                throw new RuntimeException("Raw material mapping missing for BOM_Material ID: " + material.getMaterialId());
            }

            long requiredQty = material.getQntperunit() * dto.getQuantityToProduce();
            System.out.println("   → Checking stock for: " + raw.getName());
            System.out.println("     Current stock: " + raw.getQuantity() + ", Required: " + requiredQty);

            if (raw.getQuantity() < requiredQty) {
                System.out.println("❌ Insufficient stock for: " + raw.getName());
                throw new RuntimeException("Insufficient stock for raw material: " + raw.getName());
            }
        }

        // Step 3: Deduct from inventory
        System.out.println(">>> Deducting materials from inventory...");
        for (BOM_Material material : bom.getMaterials()) {
            InventoryRawMaterial raw = material.getRawmaterial();
            long requiredQty = material.getQntperunit() * dto.getQuantityToProduce();

            double newQty = raw.getQuantity() - requiredQty;
            System.out.println("   → Updating " + raw.getName() + ": " + raw.getQuantity() + " → " + newQty);

            raw.setQuantity(newQty);
            raw.setLastUpdated(LocalDate.now());
            inventoryRawMaterialRepository.save(raw);
        }

        // Step 4: Create and save production schedule
        System.out.println(">>> Creating Production Schedule entity...");
        ProductionSchedule schedule = new ProductionSchedule();
        schedule.setBom(bom);
        schedule.setQuantityToProduce(dto.getQuantityToProduce());
        schedule.setStartDate(dto.getStartDate());
        schedule.setEndDate(dto.getEndDate());
        schedule.setStatus("Scheduled");
        schedule.setCreatedOn(LocalDate.now());

        System.out.println(">>> Saving Production Schedule to database...");
        ProductionSchedule savedSchedule = scheduleRepository.save(schedule);

        System.out.println("✅ Schedule saved successfully with ID: " + savedSchedule.getId());
        System.out.println("=== [END] createProductionSchedule() ===");
        return savedSchedule;
    }


    @Override
    public List<ProductionSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}

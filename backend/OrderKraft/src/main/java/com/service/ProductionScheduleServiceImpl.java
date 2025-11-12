package com.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.Production_ScheduleDTO;
import com.dto.ScheduleResponseDTO;
import com.entity.BOM;
import com.entity.BOM_Material;
import com.entity.InventoryRawMaterial;
import com.entity.ProductionSchedule;
import com.entity.User;
import com.repository.BOMRepository;
import com.repository.InventoryRawMaterialRepository;
import com.repository.ProductionScheduleRepository;
import com.repository.UserRepository;

@Service
@Transactional
public class ProductionScheduleServiceImpl implements ProductionScheduleService {

    @Autowired
    private BOMRepository bomRepository;

    @Autowired
    private InventoryRawMaterialRepository inventoryRawMaterialRepository;

    @Autowired
    private ProductionScheduleRepository scheduleRepository;
    
    @Autowired
    private UserRepository userRepository; // Add this

    @Override
    public ProductionSchedule createProductionSchedule(Production_ScheduleDTO dto,String username) {
        System.out.println("=== [START] createProductionSchedule() ===");
        System.out.println("Received DTO details:");
        System.out.println("   → BOM ID: " + dto.getBomId());
        System.out.println("   → Quantity to produce: " + dto.getQuantityToProduce());
        System.out.println("   → Start Date: " + dto.getStartDate());
        System.out.println("   → End Date: " + dto.getEndDate());
        System.out.println("status "+ dto.getStatus());

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
        //new code added here
        System.out.println(">>> Fetching Production Manager by username: " + username);
        User productionManager = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        // Step 4: Create and save production schedule
        System.out.println(">>> Creating Production Schedule entity...");
        ProductionSchedule schedule = new ProductionSchedule();
        schedule.setBom(bom);
        schedule.setQuantityToProduce(dto.getQuantityToProduce());
        schedule.setStartDate(dto.getStartDate());
        schedule.setEndDate(dto.getEndDate());
        schedule.setStatus(dto.getStatus());
        schedule.setCreatedOn(LocalDate.now());
        schedule.setProductionManager(productionManager);
        
     // Step 5: Fetch Production Manager
//        System.out.println(">>> Fetching Production Manager from repository...");
//        System.out.println(">>>production manager ID "+dto.getProductionManagerId());
//        User productionManager = userRepository.findById(dto.getProductionManagerId())
//                .orElseThrow(() -> new RuntimeException("Production Manager not found for ID: " + dto.getProductionManagerId()));
//        System.out.println(">>> Production Manager fetched successfully: " + productionManager.getUsername());
//
//        // Set the production manager on the schedule
//        schedule.setProductionManager(productionManager);
//        System.out.println(">>> Production Manager set on schedule: " + schedule.getProductionManager().getId());
//       
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
     // newly added methods for schedules display
    @Override
    public List<ScheduleResponseDTO> getAllSchedulesAsDTO() {
        List<ProductionSchedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ScheduleResponseDTO convertToDTO(ProductionSchedule schedule) {
        return new ScheduleResponseDTO(
            schedule.getId(),
            schedule.getStartDate(),
            schedule.getEndDate(),
            schedule.getStatus(),
            schedule.getCreatedOn(),
            schedule.getQuantityToProduce(),
            schedule.getBom() != null ? schedule.getBom().getBom_id() : null,
            schedule.getProductionManager() != null ? schedule.getProductionManager().getId() : null
        );
    }
    
    // search schedules based on manager id 
    
    @Override
    public List<ScheduleResponseDTO> getSchedulesByManagerId(Long managerId) {
        List<ProductionSchedule> schedules = scheduleRepository.findByProductionManagerId(managerId);
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

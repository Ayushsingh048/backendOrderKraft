package com.service;

import com.dto.MovementReportDTO;
import com.dto.TurnoverReportDTO;
import com.entity.InventoryRawMaterial;
import com.entity.OrderItem;
import com.entity.Category;
import com.entity.StockMovement;
import com.repository.InventoryRawMaterialRepository;
import com.repository.OrderItemRepository;
import com.repository.CategoryRepository;
import com.repository.StockMovementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ReportService {

    @Autowired private InventoryRawMaterialRepository inventoryRepo;
    @Autowired private OrderItemRepository orderItemRepo;
    @Autowired private CategoryRepository categoryRepo;
    @Autowired private StockMovementRepository movementRepo;

    // --- TURNOVER REPORT ---
    public List<TurnoverReportDTO> getTurnoverReport() {
        List<TurnoverReportDTO> report = new ArrayList<>();

        List<InventoryRawMaterial> products = inventoryRepo.findAll();

        for (InventoryRawMaterial p : products) {
            Long productId = p.getInventory_rawmaterial_id();
            String productName = p.getName();

            Category cat = categoryRepo.findById(p.getCategoryId()).orElse(null);
            String categoryName = (cat != null) ? cat.getName() : "N/A";

            // units sold
            Long unitsSold = orderItemRepo.findById(productId)
                    .stream()
                    .mapToLong(OrderItem::getQuantity)
                    .sum();

            // avg holding days (today - lastUpdated)
            double avgHoldingDays = 0.0;
            if (p.getLastUpdated() != null) {
                avgHoldingDays = ChronoUnit.DAYS.between(p.getLastUpdated(), LocalDate.now());
            }

            double turnoverRatio = (avgHoldingDays > 0) ? (unitsSold / avgHoldingDays) : 0.0;

            TurnoverReportDTO dto = new TurnoverReportDTO();
            dto.setProductId(productId);
            dto.setProductName(productName);
            dto.setCategoryName(categoryName);
            dto.setUnitsSold(unitsSold);
            dto.setAvgHoldingDays(avgHoldingDays);
            dto.setTurnoverRatio(turnoverRatio);

            report.add(dto);
        }
        return report;
    }
 // --- MOVEMENT REPORT ---
    public List<MovementReportDTO> getMovementReport(LocalDate start, LocalDate end) {
        List<MovementReportDTO> result = new ArrayList<>();
        List<StockMovement> movements = movementRepo.findAll();

        for (StockMovement m : movements) {
            if ((start == null || !m.getMovementDate().isBefore(start)) &&    // ✅ use movementDate
                (end == null || !m.getMovementDate().isAfter(end))) {

                InventoryRawMaterial product = inventoryRepo.findById(m.getProductId()).orElse(null);

                MovementReportDTO dto = new MovementReportDTO();
                dto.setMovementDate(m.getMovementDate());                     // ✅ renamed
                dto.setProductId(m.getProductId());
                dto.setProductName(product != null ? product.getName() : "N/A");
                dto.setInQty(m.getInQty());
                dto.setOutQty(m.getOutQty());
                dto.setBalanceQty(m.getBalanceQty());                         // ✅ renamed

                result.add(dto);
            }
        }
        return result;
    }
}
   
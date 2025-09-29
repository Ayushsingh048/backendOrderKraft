package com.controller;
import com.dto.InventoryStockDTO;
import com.dto.Order_ItemDTO;
import com.dto.OrderReportDTO;
import com.entity.InventoryRawMaterial;
import com.entity.Order;
import com.entity.OrderItem;
import com.entity.User;
import com.repository.CategoryRepository;
import com.repository.InventoryRawMaterialRepository;
import com.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller that exposes report endpoints used by the frontend:
 *  - GET /reports/recent-orders?limit=10
 *  - GET /reports/stock-level
 *
 * Adjust repository names or mapping method names as needed to match your project.
 */
@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "http://localhost:4200") // adjust origin if needed
public class ReportController {

    private final OrderRepository orderRepository;
    private final InventoryRawMaterialRepository inventoryRepo;
    private final CategoryRepository categoryRepo; // optional - used to lookup categoryName

    @Autowired
    public ReportController(OrderRepository orderRepository,
                            InventoryRawMaterialRepository inventoryRepo,
                            CategoryRepository categoryRepo) {
        this.orderRepository = orderRepository;
        this.inventoryRepo = inventoryRepo;
        this.categoryRepo = categoryRepo;
    }

    /**
     * Returns recent orders mapped to OrderReportDTO.
     * Example: GET /reports/recent-orders?limit=10
     */
    @GetMapping("/recent-orders")
    public ResponseEntity<List<OrderReportDTO>> getRecentOrders(@RequestParam(defaultValue = "10") int limit) {
        if (limit <= 0) limit = 10;
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "orderDate"));
        Page<Order> page = orderRepository.findAll(pageable);

        List<OrderReportDTO> dtoList = page.getContent().stream().map(this::mapOrderToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Returns inventory stock rows mapped to InventoryStockDTO.
     * Example: GET /reports/stock-level
     */
    @GetMapping("/stock-level")
    public ResponseEntity<List<InventoryStockDTO>> getStockLevel() {
        List<InventoryRawMaterial> rows = inventoryRepo.findAll();

        List<InventoryStockDTO> dto = rows.stream().map(r -> {
            InventoryStockDTO s = new InventoryStockDTO();
            // Adjust method names if your entity uses different getter names
            // Example alternatives: getInventory_rawmaterial_id(), getInventoryRawmaterialId()
            try {
                s.setInventoryRawmaterialId(getInventoryIdFromEntity(r));
            } catch (Exception ex) {
                // fallback: if method name differs, try a common alternative or set 0
                s.setInventoryRawmaterialId(0L);
            }
            s.setName(getSafeNameFromInventory(r));
            // categoryId -> try to fetch categoryName via categoryRepo if available
            Long categoryId = getCategoryIdFromInventory(r);
            s.setCategoryId(categoryId);
            if (categoryId != null && categoryRepo != null) {
                categoryRepo.findById(categoryId).ifPresent(cat -> {
                    // assumes category entity has getName() method
                    try {
                        s.setCategoryName((String) cat.getClass().getMethod("getName").invoke(cat));
                    } catch (Exception ignored) {
                    }
                });
            }
            s.setQuantity(getQuantityFromInventory(r));
            s.setLastUpdated(getLastUpdatedFromInventory(r));
            return s;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dto);
    }

    /* ----------------- Mapping helpers ----------------- */

    private OrderReportDTO mapOrderToDto(Order o) {
        OrderReportDTO dto = new OrderReportDTO();
        dto.setOrderId(o.getOrderId());
        dto.setOrderDate(o.getOrderDate());
        dto.setDeliveryDate(o.getDeliveryDate());
        dto.setStatus(o.getStatus());
        dto.setTotalAmount(o.getTotalAmount());
        dto.setOrderName(o.getOrderName());
        dto.setSupplierId(o.getSupplierId());

        // procurementOfficer: adapt to the actual getters of your User entity.
        User officer = o.getProcurementOfficer();
        if (officer != null) {
            // Most projects have firstName/lastName or fullName. Adjust if needed.
            String name = buildNameFromUser(officer);
            dto.setProcurementOfficerName(name);
        }

        // Map order items
        List<OrderItem> items = o.getOrderItems();
        if (items != null) {
            List<Order_ItemDTO> itemDtos = items.stream().map(this::mapOrderItemToDto).collect(Collectors.toList());
            dto.setItems(itemDtos);
        }
        return dto;
    }

    private Order_ItemDTO mapOrderItemToDto(OrderItem oi) {
        Order_ItemDTO idto = new Order_ItemDTO();
        idto.setOrder_id(oi.getOrderItemId());
        idto.setName(oi.getName());
        idto.setQuantity(oi.getQuantity());
        idto.setUnit_price(oi.getUnitPrice());
        return idto;
    }

    /* ----------------- Small reflection-free safe accessors ----------------- */
    // The following helper methods try to read common getter names from your InventoryRawMaterial entity.
    // If your entity uses other method names, replace the body by direct calls, e.g. r.getInventoryRawmaterialId()

    private long getInventoryIdFromEntity(InventoryRawMaterial r) {
        // try common getters; replace with direct call if you know exact name
        try { return (long) r.getClass().getMethod("getInventoryRawmaterialId").invoke(r); } catch (Exception ignored) {}
        try { return (long) r.getClass().getMethod("getInventory_rawmaterial_id").invoke(r); } catch (Exception ignored) {}
        try { return (long) r.getClass().getMethod("getId").invoke(r); } catch (Exception ignored) {}
        return 0L;
    }

    private String getSafeNameFromInventory(InventoryRawMaterial r) {
        try { Object v = r.getClass().getMethod("getName").invoke(r); if (v!=null) return v.toString(); } catch (Exception ignored) {}
        try { Object v = r.getClass().getMethod("getRawMaterialName").invoke(r); if (v!=null) return v.toString(); } catch (Exception ignored) {}
        return null;
    }

    private Long getCategoryIdFromInventory(InventoryRawMaterial r) {
        try { Object v = r.getClass().getMethod("getCategoryId").invoke(r); if (v instanceof Number) return ((Number) v).longValue(); } catch (Exception ignored) {}
        try { Object v = r.getClass().getMethod("getCategory").invoke(r); if (v instanceof Number) return ((Number)v).longValue(); } catch (Exception ignored) {}
        return null;
    }

    private long getQuantityFromInventory(InventoryRawMaterial r) {
        try { Object v = r.getClass().getMethod("getQuantity").invoke(r); if (v instanceof Number) return ((Number) v).longValue(); } catch (Exception ignored) {}
        return 0L;
    }

    private java.time.LocalDate getLastUpdatedFromInventory(InventoryRawMaterial r) {
        try { Object v = r.getClass().getMethod("getLastUpdated").invoke(r); if (v instanceof java.time.LocalDate) return (java.time.LocalDate) v; } catch (Exception ignored) {}
        try { Object v = r.getClass().getMethod("getUpdatedAt").invoke(r); if (v instanceof java.time.LocalDate) return (java.time.LocalDate) v; } catch (Exception ignored) {}
        return null;
    }

    private String buildNameFromUser(User user) {
        // try common getters; if none exists, return user.toString()
        try {
            Object f = user.getClass().getMethod("getFirstName").invoke(user);
            Object l = user.getClass().getMethod("getLastName").invoke(user);
            if (f != null && l != null) return f.toString() + " " + l.toString();
        } catch (Exception ignored) {}
        try {
            Object full = user.getClass().getMethod("getFullName").invoke(user);
            if (full != null) return full.toString();
        } catch (Exception ignored) {}
        try {
            Object username = user.getClass().getMethod("getUsername").invoke(user);
            if (username != null) return username.toString();
        } catch (Exception ignored) {}
        // fallback
        return String.valueOf(user.getClass().getName() + "#" + user.hashCode());
    }
}



















//package com.controller;
//
//import com.entity.Order;
//import com.entity.InventoryRawMaterial;
//import com.repository.OrderRepository;
//import com.repository.InventoryRawMaterialRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/reports")
//public class ReportController {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private InventoryRawMaterialRepository inventoryRepo;
//
//    // GET /reports/recent-orders?limit=10
//    @GetMapping("/recent-orders")
//    public ResponseEntity<List<Order>> getRecentOrders(@RequestParam(defaultValue = "10") int limit) {
//        Pageable page = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "orderDate"));
//        Page<Order> ordersPage = orderRepository.findAll(page);
//        return ResponseEntity.ok(ordersPage.getContent());
//    }
//
//    // GET /reports/stock-level
//    @GetMapping("/stock-level")
//    public ResponseEntity<List<InventoryRawMaterial>> getStockLevel() {
//        return ResponseEntity.ok(inventoryRepo.findAll());
//    }
//}

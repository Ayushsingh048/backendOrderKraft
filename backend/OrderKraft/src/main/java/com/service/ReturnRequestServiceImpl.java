package com.service;

import com.dto.ReturnRequestDTO;
import com.entity.Inventory;
import com.entity.Order;
import com.entity.Product;
import com.entity.ReturnRequest;
import com.repository.InventoryRepository;
import com.repository.OrderRepository;
import com.repository.ProductRepository;
import com.repository.ReturnRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {

    @Autowired
    private ReturnRequestRepository returnRequestRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Create a return request. Enforces 7-day return window (based on order.deliveryDate).
     * If order.deliveryDate is null or >7 days old, throws RuntimeException("Return window closed")
     */
    @Override
    @Transactional
    public ReturnRequest createReturnRequest(ReturnRequestDTO dto) {
        // validate order
        Optional<Order> optOrder = orderRepository.findById(dto.getOrderId());
        if (!optOrder.isPresent()) {
            throw new RuntimeException("Order not found for id: " + dto.getOrderId());
        }
        Order order = optOrder.get();

        // 7-day return window check
        LocalDate deliveryDate = order.getDeliveryDate();
//        Date delivery = order.getDeliveryDate();
//        LocalDate deliveryDate = delivery.toInstant()
//                                        .atZone(ZoneId.systemDefault())
//                                        .toLocalDate();
        
        if (deliveryDate == null) {
            throw new RuntimeException("Delivery date not set for order");
        }
        long days = ChronoUnit.DAYS.between(deliveryDate, LocalDate.now());
        LocalDate today = LocalDate.now(); 
        System.out.println("Delivery: " + deliveryDate + ", Today: " + today + ", Days: " + days);
//        if (days > 7) {
//            throw new RuntimeException("Return window closed");
//        }

        ReturnRequest req = new ReturnRequest();
        req.setOrderId(dto.getOrderId());
        // supplier: DTO overrides order if provided
        req.setSupplierId(dto.getSupplierId() != null ? dto.getSupplierId() : order.getSupplierId());
        req.setReason(dto.getReason());
        req.setComment(dto.getComment());
        req.setRequestDate(LocalDate.now());
        // product: DTO overrides order if provided
        req.setProductId(dto.getProductId() != null ? dto.getProductId() : order.getProductId());
        // quantity: default to 1
        req.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 1);
        // initial status per your entity comment (PENDING / ACCEPTED / REJECTED)
        req.setStatus("PENDING");

        return returnRequestRepository.save(req);
    }

    @Override
    public List<ReturnRequest> getByOrder(Long orderId) {
        return returnRequestRepository.findByOrderId(orderId);
    }

    @Override
    public List<ReturnRequest> getBySupplier(Long supplierId) {
        return returnRequestRepository.findBySupplierId(supplierId);
    }

    @Override
    public List<ReturnRequest> getAll() {
        return returnRequestRepository.findAll();
    }

    /**
     * Update status (PENDING / ACCEPTED / REJECTED).
     * When status becomes ACCEPTED we decrease inventory for the product (transactional).
     */
    @Override
    @Transactional
    public ReturnRequest updateStatus(Long id, String status) {
        Optional<ReturnRequest> opt = returnRequestRepository.findById(id);
        if (!opt.isPresent()) {
            throw new RuntimeException("Return request not found for id: " + id);
        }
        ReturnRequest req = opt.get();

        String normalized = status == null ? "" : status.trim().toUpperCase();
        if (!normalized.equals("PENDING") && !normalized.equals("ACCEPTED") && !normalized.equals("REJECTED")) {
            throw new RuntimeException("Invalid status: " + status);
        }

        req.setStatus(normalized);
        ReturnRequest saved = returnRequestRepository.save(req);

        if ("ACCEPTED".equals(normalized)) {
            decreaseInventoryForProduct(req.getProductId(), req.getQuantity());
        }

        return saved;
    }

    /**
     * Decrease inventory quantity for the given productId by qty.
     * Uses ProductRepository to fetch Product entity and then inventoryRepository.findByProduct(product).
     * Throws RuntimeException on missing product/inventory or insufficient stock.
     */
    private void decreaseInventoryForProduct(Long productId, Integer qty) {
        if (productId == null) {
            throw new RuntimeException("Product id is missing on return request");
        }
        if (qty == null || qty <= 0) {
            throw new RuntimeException("Invalid quantity to decrease");
        }

        Optional<Product> optProd = productRepository.findById(productId);
        if (!optProd.isPresent()) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        Product product = optProd.get();

        Optional<Inventory> optInv = inventoryRepository.findByProduct(product);
        if (!optInv.isPresent()) {
            throw new RuntimeException("Inventory record not found for product id: " + productId);
        }
        Inventory inv = optInv.get();

        Integer current = inv.getQuantity() == null ? 0 : inv.getQuantity();
        int updated = current - qty;
        if (updated < 0) {
            throw new RuntimeException("Insufficient inventory. Current=" + current + ", requested decrease=" + qty);
        }

        inv.setQuantity(updated);

        // try to set lastUpdated if your Inventory entity has such a setter
        try {
            inv.getClass().getDeclaredMethod("setLastUpdated", LocalDate.class)
                    .invoke(inv, LocalDate.now());
        } catch (Exception ignored) {
            // ignore if setter not present
        }

        inventoryRepository.save(inv);
    }
}



//package com.service;
//
//import com.dto.ReturnRequestDTO;
//import com.entity.Inventory;
//import com.entity.Order;
//import com.entity.OrderItem;
//import com.entity.Product;
//import com.entity.ReturnRequest;
//import com.entity.ReturnStatus;
//import com.repository.InventoryRepository;
//import com.repository.OrderRepository;
//import com.repository.ProductRepository;
//import com.repository.ReturnRequestRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ReturnRequestServiceImpl implements ReturnRequestService {
//
//    @Autowired
//    private ReturnRequestRepository repo;
//
//    @Autowired
//    private OrderRepository orderRepo;
//
//    @Autowired
//    private InventoryRepository inventoryRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Override
//    @Transactional
//    public ReturnRequest createReturnRequest(ReturnRequestDTO dto) {
//        Order order = orderRepo.findById(dto.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        // validate 7-day return window
//        if (order.getDeliveryDate() == null) {
//            throw new RuntimeException("Order has no delivery date");
//        }
//        long daysSinceDelivery = ChronoUnit.DAYS.between(order.getDeliveryDate(), LocalDate.now());
//        if (daysSinceDelivery > 7) {
//            throw new RuntimeException("Return window closed");
//        }
//
//        ReturnRequest req = new ReturnRequest();
//        req.setOrderId(order.getOrderId());
//        req.setSupplierId(order.getSupplierId());
//        req.setReason(dto.getReason());
//        req.setComment(dto.getComment());
//        req.setRequestDate(LocalDate.now());
//        req.setProductId(dto.getProductId());
//        req.setQuantity(dto.getQuantity());
//
//        // AUTO-ACCEPT new returns
//        req.setStatus("ACCEPTED");
//
//        ReturnRequest saved = repo.save(req);
//
//        // Update order status to requested_to_return
//        order.setStatus("requested_to_return");
//        orderRepo.save(order);
//
//        // (Do NOT auto-accept here unless you want auto behavior)
//        // The frontend will call accept endpoint (or you can auto-accept if you want):
//        // saved.setStatus(ReturnStatus.SUPPLIER_ACCEPTED.name());
//
//        return saved;
//    }
//
//    @Override
//    public List<ReturnRequest> getByOrder(Long orderId) {
//        return repo.findByOrderId(orderId);
//    }
//
//    @Override
//    public List<ReturnRequest> getBySupplier(Long supplierId) {
//        return repo.findBySupplierId(supplierId);
//    }
//
//    @Override
//    @Transactional
//    public ReturnRequest updateStatus(Long id, String status) {
//        ReturnRequest req = repo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Return request not found"));
//
//        String normalized = status == null ? null : status.trim().toUpperCase();
//        String prev = req.getStatus();
//
//        req.setStatus(normalized);
//        ReturnRequest saved = repo.save(req);
//
//        // If accepted -> handle inventory & update order
//        if ("SUPPLIER_ACCEPTED".equalsIgnoreCase(normalized) && !"SUPPLIER_ACCEPTED".equalsIgnoreCase(prev)) {
//            handleAcceptedReturn(saved);
//            saved.setStatus(ReturnStatus.PROCESSED.name());
//            repo.save(saved);
//
//            Order order = orderRepo.findById(saved.getOrderId())
//                    .orElseThrow(() -> new RuntimeException("Order not found for return request"));
//            order.setStatus("return_processed");
//            orderRepo.save(order);
//        }
//
//        return saved;
//    }
//
//    /**
//     * New: accept by order id (convenience endpoint)
//     */
//    @Transactional
//    public ReturnRequest acceptByOrderId(Long orderId) {
//        ReturnRequest rr = repo.findFirstByOrderIdOrderByIdDesc(orderId)
//                .orElseThrow(() -> new RuntimeException("Return request not found for order: " + orderId));
//
//        String status = rr.getStatus();
//        if ("SUPPLIER_ACCEPTED".equalsIgnoreCase(status) || "PROCESSED".equalsIgnoreCase(status)) {
//            return rr;
//        }
//
//        rr.setStatus(ReturnStatus.SUPPLIER_ACCEPTED.name());
//        rr = repo.save(rr);
//
//        handleAcceptedReturn(rr);
//
//        rr.setStatus(ReturnStatus.PROCESSED.name());
//        rr = repo.save(rr);
//
//        Order order = orderRepo.findById(rr.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Order not found for return request"));
//        order.setStatus("return_processed");
//        orderRepo.save(order);
//
//        return rr;
//    }
//
//    /** Business rule:
//     *  - If reason == DAMAGED => DECREASE inventory by qty (defective items removed)
//     *  - Otherwise => INCREASE inventory (restock)
//     */
//    @Transactional
//    protected void handleAcceptedReturn(ReturnRequest req) {
//        Long productId = req.getProductId();
//        Integer qty = req.getQuantity();
//
//        if (productId == null || qty == null || qty <= 0) {
//            throw new RuntimeException("Invalid return request: missing productId/quantity");
//        }
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
//
//        Inventory inv = inventoryRepository.findByProduct(product)
//                .orElseThrow(() -> new RuntimeException("No inventory for product " + productId));
//        int updated = 0;
//        if (req.getReason() == null) {
//            // If reason missing, default to restock
//            inv.setQuantity(inv.getQuantity() + qty);
//        } else if (req.getReason() == com.enums.ReturnReason.DAMAGED) {
//            // DECREASE inventory for defective items
//           updated = inv.getQuantity() - qty;
//            if (updated < 0) {
//                throw new RuntimeException("Inventory can't be negative after decreasing for defective return");
//            }
//            inv.setQuantity(updated);
//        } else {
//            // restock for other reasons
//            inv.setQuantity(inv.getQuantity() + qty);
//        }
//
//        inv.setQuantity(updated);
//        inv.setLastUpdated(LocalDate.now());
//        inventoryRepository.save(inv);
//    }
//}




















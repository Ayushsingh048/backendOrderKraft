package com.service;

import com.dto.ReturnRequestDTO;
import com.entity.Inventory;
import com.entity.Order;
import com.entity.OrderItem;
import com.entity.Product;
import com.entity.ReturnRequest;
import com.entity.ReturnStatus;
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
    private ReturnRequestRepository repo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public ReturnRequest createReturnRequest(ReturnRequestDTO dto) {
        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // validate 7-day return window
        if (order.getDeliveryDate() == null) {
            throw new RuntimeException("Order has no delivery date");
        }
        long daysSinceDelivery = ChronoUnit.DAYS.between(order.getDeliveryDate(), LocalDate.now());
        if (daysSinceDelivery > 7) {
            throw new RuntimeException("Return window closed");
        }

        ReturnRequest req = new ReturnRequest();
        req.setOrderId(order.getOrderId());
        req.setSupplierId(order.getSupplierId());
        req.setReason(dto.getReason());
        req.setComment(dto.getComment());
        req.setRequestDate(LocalDate.now());
        req.setProductId(dto.getProductId());
        req.setQuantity(dto.getQuantity());

        // AUTO-ACCEPT new returns
        req.setStatus("ACCEPTED");

        ReturnRequest saved = repo.save(req);

        // Update order status to requested_to_return
        order.setStatus("requested_to_return");
        orderRepo.save(order);

        // (Do NOT auto-accept here unless you want auto behavior)
        // The frontend will call accept endpoint (or you can auto-accept if you want):
        // saved.setStatus(ReturnStatus.SUPPLIER_ACCEPTED.name());

        return saved;
    }

    @Override
    public List<ReturnRequest> getByOrder(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    @Override
    public List<ReturnRequest> getBySupplier(Long supplierId) {
        return repo.findBySupplierId(supplierId);
    }

    @Override
    @Transactional
    public ReturnRequest updateStatus(Long id, String status) {
        ReturnRequest req = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        String normalized = status == null ? null : status.trim().toUpperCase();
        String prev = req.getStatus();

        req.setStatus(normalized);
        ReturnRequest saved = repo.save(req);

        // If accepted -> handle inventory & update order
        if ("SUPPLIER_ACCEPTED".equalsIgnoreCase(normalized) && !"SUPPLIER_ACCEPTED".equalsIgnoreCase(prev)) {
            handleAcceptedReturn(saved);
            saved.setStatus(ReturnStatus.PROCESSED.name());
            repo.save(saved);

            Order order = orderRepo.findById(saved.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found for return request"));
            order.setStatus("return_processed");
            orderRepo.save(order);
        }

        return saved;
    }

    /**
     * New: accept by order id (convenience endpoint)
     */
    @Transactional
    public ReturnRequest acceptByOrderId(Long orderId) {
        ReturnRequest rr = repo.findFirstByOrderIdOrderByIdDesc(orderId)
                .orElseThrow(() -> new RuntimeException("Return request not found for order: " + orderId));

        String status = rr.getStatus();
        if ("SUPPLIER_ACCEPTED".equalsIgnoreCase(status) || "PROCESSED".equalsIgnoreCase(status)) {
            return rr;
        }

        rr.setStatus(ReturnStatus.SUPPLIER_ACCEPTED.name());
        rr = repo.save(rr);

        handleAcceptedReturn(rr);

        rr.setStatus(ReturnStatus.PROCESSED.name());
        rr = repo.save(rr);

        Order order = orderRepo.findById(rr.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found for return request"));
        order.setStatus("return_processed");
        orderRepo.save(order);

        return rr;
    }

    /** Business rule:
     *  - If reason == DAMAGED => DECREASE inventory by qty (defective items removed)
     *  - Otherwise => INCREASE inventory (restock)
     */
    @Transactional
    protected void handleAcceptedReturn(ReturnRequest req) {
        Long productId = req.getProductId();
        Integer qty = req.getQuantity();

        if (productId == null || qty == null || qty <= 0) {
            throw new RuntimeException("Invalid return request: missing productId/quantity");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        Inventory inv = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("No inventory for product " + productId));
        int updated = 0;
        if (req.getReason() == null) {
            // If reason missing, default to restock
            inv.setQuantity(inv.getQuantity() + qty);
        } else if (req.getReason() == com.enums.ReturnReason.DAMAGED) {
            // DECREASE inventory for defective items
           updated = inv.getQuantity() - qty;
            if (updated < 0) {
                throw new RuntimeException("Inventory can't be negative after decreasing for defective return");
            }
            inv.setQuantity(updated);
        } else {
            // restock for other reasons
            inv.setQuantity(inv.getQuantity() + qty);
        }

        inv.setQuantity(updated);
        inv.setLastUpdated(LocalDate.now());
        inventoryRepository.save(inv);
    }
}



















//package com.service;
//import com.dto.ReturnRequestDTO;
//import com.entity.Inventory;
//import com.entity.Order;
//import com.entity.Product;
//import com.entity.ReturnRequest;
//import com.entity.ReturnStatus;
//import com.enums.ReturnReason;
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
//        // validate 7-day return window (server-side enforcement)
//        if (order.getDeliveryDate() == null) {
//            throw new RuntimeException("Order has no delivery date");
//        }
//        long daysSinceDelivery = ChronoUnit.DAYS.between(order.getDeliveryDate(), LocalDate.now());
//        if (daysSinceDelivery > 7) {
//            throw new RuntimeException("Return window closed");
//        }
//        if (daysSinceDelivery < 0) {
//            throw new RuntimeException("Delivery date is in the future.");
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
//        // initially mark as RAISED
//        req.setStatus(ReturnStatus.RAISED.name());
//
//        ReturnRequest saved = repo.save(req);
//
//        // Update order status to requested_to_return
//        order.setStatus("requested_to_return");
//        orderRepo.save(order);
//
//        // Auto-accept for now (supplier auto approves in this flow)
//        saved.setStatus(ReturnStatus.SUPPLIER_ACCEPTED.name());
//        saved = repo.save(saved);
//
//        // Process accepted return: DECREASE inventory for defective returns
//        handleAcceptedReturn(saved);
//
//        // Mark as processed
//        saved.setStatus(ReturnStatus.PROCESSED.name());
//        saved = repo.save(saved);
//
//        // Update order status to reflect return processed
//        order.setStatus("return_processed");
//        orderRepo.save(order);
//
//        return saved;
//    }
//
//    @Override
//    public List<ReturnRequest> getByOrder(Long orderId) {
//        return repo.findByOrderId(orderId);
//    }
//
//    public List<ReturnRequest> getBySupplier(Long supplierId) {
//        return repo.findBySupplierId(supplierId);
//    }
//    @Transactional
//    public ReturnRequest acceptByOrderId(Long orderId) {
//        // find latest return request for order
//        ReturnRequest rr = repo.findFirstByOrderIdOrderByIdDesc(orderId)
//                .orElseThrow(() -> new RuntimeException("Return request not found for order: " + orderId));
//
//        // If already accepted/processed then just return
//        String status = rr.getStatus();
//        if ("SUPPLIER_ACCEPTED".equalsIgnoreCase(status) || "PROCESSED".equalsIgnoreCase(status)) {
//            return rr;
//        }
//
//        // update status to supplier accepted
//        rr.setStatus("SUPPLIER_ACCEPTED");
//        rr = repo.save(rr);
//
//        // handle inventory decrease/increase
//        handleAcceptedReturn(rr);
//
//        // mark processed
//        rr.setStatus("PROCESSED");
//        rr = repo.save(rr);
//
//        // update corresponding order status
//        Order order = orderRepo.findById(rr.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Order not found for return request"));
//        order.setStatus("return_processed");
//        orderRepo.save(order);
//
//        return rr;
//    }
//
//
//    public List<ReturnRequest> getAll() {
//        return repo.findAll();
//    }
//
//    @Override
//    @Transactional
//    public ReturnRequest updateStatus(Long id, String status) {
//        ReturnRequest req = repo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Return request not found"));
//
//        String normalized = status == null ? null : status.trim().toUpperCase();
//
//        // Only block invalid transitions if you want, otherwise just save
//        req.setStatus(normalized);
//        ReturnRequest saved = repo.save(req);
//
//        // Optional: Only handle inventory if moving to SUPPLIER_ACCEPTED
//        if ("SUPPLIER_ACCEPTED".equalsIgnoreCase(normalized) && ! "SUPPLIER_ACCEPTED".equalsIgnoreCase(req.getStatus())) {
//            handleAcceptedReturn(saved);
//            saved.setStatus(ReturnStatus.PROCESSED.name());
//            repo.save(saved);
//
//            // update order status
//            Order order = orderRepo.findById(saved.getOrderId())
//                    .orElseThrow(() -> new RuntimeException("Order not found for return request"));
//            order.setStatus("return_processed");
//            orderRepo.save(order);
//        }
//
//        return saved;
//    }
//
//
//    /**
//     * Main logic to handle accepted return:
//     * - For defective reasons (e.g. DAMAGED) -> DECREASE inventory by returned qty.
//     * - For non-defective reasons -> increase inventory (restock) [keeps backward compatibility].
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
//        // Load product
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
//
//        // Find inventory by product
//        Inventory inv = inventoryRepository.findByProduct(product)
//                .orElseThrow(() -> new RuntimeException("No inventory for product " + productId));
//
//        // Business rule: if return reason indicates defect/damage, DECREASE stock.
//        if (req.getReason() == ReturnReason.DAMAGED) {
//            int updated = inv.getQuantity() - qty;
//            if (updated < 0) {
//                // either set 0 or throw — choose throw to signal mismatch (you can change to set 0)
//                throw new RuntimeException("Inventory can't be negative after decreasing for defective return");
//            }
//            inv.setQuantity(updated);
//        } else {
//            // default: restock returned items
//            int updated = inv.getQuantity() + qty;
//            inv.setQuantity(updated);
//        }
//
//        inv.setLastUpdated(LocalDate.now());
//        inventoryRepository.save(inv);
//    }
//}



//package com.service;
//
//import com.dto.ReturnRequestDTO;
//import com.entity.Inventory;
//import com.entity.Product;
//import com.entity.ReturnRequest;
//import com.entity.ReturnStatus;
//import com.repository.InventoryRepository;
//import com.repository.ProductRepository;
//import com.repository.ReturnRequestRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class ReturnRequestServiceImpl implements ReturnRequestService {
//
//    @Autowired
//    private ReturnRequestRepository returnRequestRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private InventoryRepository inventoryRepository;
//
//    @Override
//    public ReturnRequest createReturnRequest(ReturnRequestDTO dto) {
//        ReturnRequest rr = new ReturnRequest();
//        rr.setOrderId(dto.getOrderId());
//        rr.setProductId(dto.getProductId());
//        rr.setQuantity(dto.getQuantity());
//        rr.setReason(dto.getReason());
//        rr.setRequestDate(LocalDate.now());
//        rr.setStatus(ReturnStatus.RAISED.name());
//        return returnRequestRepository.save(rr);
//    }
//
//    @Override
//    public List<ReturnRequest> getByOrder(Long orderId) {
//        return returnRequestRepository.findByOrderId(orderId);
//    }
//
//    /**
//     * Update the status of a return request.
//     * When status changes to SUPPLIER_ACCEPTED we decrease the inventory quantity.
//     *
//     * The method is transactional so the status update and inventory update happen in the same transaction.
//     */
//    @Override
//    @Transactional
//    public ReturnRequest updateStatus(Long id, String statusStr) {
//        ReturnRequest rr = returnRequestRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("ReturnRequest not found for id: " + id));
//
//        // Validate/convert status string
//        ReturnStatus newStatus;
//        try {
//            newStatus = ReturnStatus.valueOf(statusStr);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Invalid return status: " + statusStr);
//        }
//
//        // If supplier accepted, decrement inventory.
//        if (newStatus == ReturnStatus.SUPPLIER_ACCEPTED) {
//            decreaseInventoryForReturn(rr);
//        }
//
//        rr.setStatus(newStatus.name());
//        return returnRequestRepository.save(rr);
//    }
//
//    /**
//     * Decreases inventory by the quantity in the return request.
//     * Throws RuntimeException if product or inventory row not found or if decrease would go negative.
//     *
//     * Note: this method is called from a @Transactional method above.
//     */
//    @Transactional
//    protected void decreaseInventoryForReturn(ReturnRequest rr) {
//        Long productId = rr.getProductId();
//        Integer returnQty = rr.getQuantity() == null ? 0 : rr.getQuantity();
//
//        if (returnQty <= 0) {
//            // nothing to decrease
//            return;
//        }
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
//
//        Inventory inv = inventoryRepository.findByProduct(product)
//                .orElseThrow(() -> new RuntimeException("No inventory record for product: " + productId));
//
//        int current = inv.getQuantity() == null ? 0 : inv.getQuantity();
//        int updated = current - returnQty;
//
//        if (updated < 0) {
//            // Defensive: avoid negative stock. If your business needs allow negative stock,
//            // remove this check and set updated to 'updated' (negative) or handle differently.
//            throw new RuntimeException("Insufficient inventory to decrease: current=" + current + ", requestedDecrease=" + returnQty);
//        }
//
//        inv.setQuantity(updated);
//        inv.setLastUpdated(LocalDate.now());
//        inventoryRepository.save(inv);
//    }
//}

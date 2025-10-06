package com.service;

import com.dto.ReturnRequestDTO;
import com.entity.Inventory;
import com.entity.Order;
import com.entity.OrderItem;
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

        // Immediately decrease inventory if accepted
        handleAcceptedReturn(saved);

        return saved;
    }

    @Override
    public List<ReturnRequest> getByOrder(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public ReturnRequest updateStatus(Long id, String status) {
        ReturnRequest req = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        String normalized = status == null ? null : status.toUpperCase();
        req.setStatus(normalized);
        ReturnRequest saved = repo.save(req);

        // decrease inventory only when accepted
        if ("ACCEPTED".equalsIgnoreCase(normalized)) {
            handleAcceptedReturn(saved);
        }

        return saved;
    }

    /**
     * Main logic to handle accepted return: decrease product inventory.
     */
    @Transactional
    protected void handleAcceptedReturn(ReturnRequest req) {
        Long productId = req.getProductId();
        Integer qty = req.getQuantity();

        if (productId == null || qty == null || qty <= 0) {
            throw new RuntimeException("Invalid return request: missing productId/quantity");
        }

        // Load product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        // Find inventory by product
        Inventory inv = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("No inventory for product " + productId));

        // ✅ Increase stock (returns add items back)
//        int updated = inv.getQuantity() + qty;
        int updated = inv.getQuantity() - qty; // decrease


        inv.setQuantity(updated);
        inv.setLastUpdated(LocalDate.now());
        inventoryRepository.save(inv);
    }
}



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

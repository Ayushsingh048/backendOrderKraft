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

        // NEW: persist productId / quantity if provided by frontend
        try {
            if (dto.getProductId() != null) {
                // Attempt to set via standard setter if exists
                req.setProductId(dto.getProductId());
            }
        } catch (Throwable ignored) { }

        try {
            if (dto.getQuantity() != null) {
                req.setQuantity(dto.getQuantity());
            }
        } catch (Throwable ignored) { }

        // AUTO-ACCEPT (as you requested)
        req.setStatus("ACCEPTED");

        ReturnRequest saved = repo.save(req);

        // Immediately handle accepted return: decrease inventory
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

        // if status changed to ACCEPTED, decrease inventory
        if ("ACCEPTED".equalsIgnoreCase(normalized)) {
            handleAcceptedReturn(saved);
        }

        return saved;
    }

    /**
     * Main logic to handle an accepted return request.
     * Tries a few common strategies to find product(s) and quantity.
     * Keep the method names in sync with your entities if they differ.
     */
    @Transactional
    protected void handleAcceptedReturn(ReturnRequest req) {
        // Attempt 1: if ReturnRequest already stores productId & quantity (common)
        try {
            Long productId = null;
            Integer qty = null;

            try {
                Object pid = ReturnRequest.class.getMethod("getProductId").invoke(req);
                if (pid instanceof Long) productId = (Long) pid;
                else if (pid instanceof Integer) productId = ((Integer) pid).longValue();
            } catch (NoSuchMethodException ignored) { }

            try {
                Object qObj = ReturnRequest.class.getMethod("getQuantity").invoke(req);
                if (qObj instanceof Integer) qty = (Integer) qObj;
                else if (qObj instanceof Long) qty = ((Long) qObj).intValue();
            } catch (NoSuchMethodException ignored) { }

            if (productId != null && qty != null && qty > 0) {
                decreaseInventoryByProductId(productId, qty);
                return;
            }
        } catch (Exception ignored) { }

        // Attempt 2: derive from referenced Order -> OrderItems
        if (req.getOrderId() != null) {
            Optional<Order> optOrder = orderRepo.findById(req.getOrderId());
            if (!optOrder.isPresent()) return;
            Order order = optOrder.get();

            List<OrderItem> items = null;
            try {
                items = order.getOrderItems();
            } catch (Throwable t) { }

            if (items == null) return;

            for (OrderItem item : items) {
                Long productId = null;
                int qty = 0;

                // Try item.getProduct().getProduct_id() or getId()
                try {
                    Object prodObj = null;
                    try {
                        prodObj = OrderItem.class.getMethod("getProduct").invoke(item);
                    } catch (NoSuchMethodException ignored) { }

                    if (prodObj instanceof Product) {
                        try {
                            Object pidObj = Product.class.getMethod("getProduct_id").invoke(prodObj);
                            if (pidObj instanceof Long) productId = (Long) pidObj;
                            else if (pidObj instanceof Integer) productId = ((Integer) pidObj).longValue();
                        } catch (NoSuchMethodException nmEx) {
                            try {
                                Object pidObj2 = Product.class.getMethod("getId").invoke(prodObj);
                                if (pidObj2 instanceof Long) productId = (Long) pidObj2;
                                else if (pidObj2 instanceof Integer) productId = ((Integer) pidObj2).longValue();
                            } catch (Exception ignored2) { }
                        }
                    }
                } catch (Exception ignored) { }

                // Try direct orderItem product id getters
                if (productId == null) {
                    try {
                        Object pid = null;
                        try {
                            pid = OrderItem.class.getMethod("getProductId").invoke(item);
                        } catch (NoSuchMethodException ignored) {
                            pid = OrderItem.class.getMethod("getProduct_id").invoke(item);
                        }
                        if (pid instanceof Long) productId = (Long) pid;
                        else if (pid instanceof Integer) productId = ((Integer) pid).longValue();
                    } catch (Exception ignored) { }
                }

                // Resolve by name if still not found
                if (productId == null) {
                    try {
                        String name = null;
                        try {
                            name = (String) OrderItem.class.getMethod("getName").invoke(item);
                        } catch (NoSuchMethodException ignored) { }

                        if (name != null && !name.isEmpty()) {
                            Optional<Product> pOpt = productRepository.findByName(name);
                            if (pOpt.isPresent()) {
                                Product p = pOpt.get();
                                try {
                                    Object pidObj = Product.class.getMethod("getProduct_id").invoke(p);
                                    if (pidObj instanceof Long) productId = (Long) pidObj;
                                    else if (pidObj instanceof Integer) productId = ((Integer) pidObj).longValue();
                                } catch (NoSuchMethodException nmEx) {
                                    try {
                                        Object pidObj2 = Product.class.getMethod("getId").invoke(p);
                                        if (pidObj2 instanceof Long) productId = (Long) pidObj2;
                                        else if (pidObj2 instanceof Integer) productId = ((Integer) pidObj2).longValue();
                                    } catch (Exception ignored2) { }
                                }
                            }
                        }
                    } catch (Exception ignored) { }
                }

                // Get item quantity
                try {
                    Object qObj = null;
                    try {
                        qObj = OrderItem.class.getMethod("getQuantity").invoke(item);
                    } catch (NoSuchMethodException ignored) {
                        qObj = OrderItem.class.getMethod("getQty").invoke(item);
                    }
                    if (qObj instanceof Integer) qty = (Integer) qObj;
                    else if (qObj instanceof Long) qty = ((Long) qObj).intValue();
                } catch (Exception ignored) { }

                if (productId != null && qty > 0) {
                    decreaseInventoryByProductId(productId, qty);
                }
            }
        }
    }

    /**
     * Use productRepository.findById(...) and inventoryRepository.findByProduct(product).
     * This avoids explicit JPQL and uses a derived repository method.
     */
    @Transactional
    protected void decreaseInventoryByProductId(Long productId, int qty) {
        if (productId == null || qty <= 0) return;

        // Load Product entity first (so we don't need explicit JPQL in the repository)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        // Use the derived repository method (InventoryRepository#findByProduct)
        Optional<Inventory> invOpt = inventoryRepository.findByProduct(product);
        if (!invOpt.isPresent()) {
            // choose behavior: throw or log; current behavior throws so you notice missing inventory rows
            throw new RuntimeException("Inventory entry not found for product id: " + productId);
        }

        Inventory inv = invOpt.get();
        int current;
        try {
            current = inv.getQuantity();
        } catch (Throwable t) {
            throw new RuntimeException("Inventory entity does not support getQuantity()");
        }

        int updated = current - qty;
        if (updated < 0) updated = 0;

        inv.setQuantity(updated);

        try {
            inv.setLastUpdated(LocalDate.now());
        } catch (Throwable ignored) { }

        inventoryRepository.save(inv);
    }
}

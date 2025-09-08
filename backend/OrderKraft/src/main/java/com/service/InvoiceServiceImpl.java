package com.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.dto.InvoiceDTO;
import com.dto.Order_ItemDTO;
import com.entity.Invoice;
import com.entity.Order;
import com.entity.OrderItem;
import com.entity.Payment;
import com.repository.InvoiceRepository;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;
import com.repository.PaymentRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final PdfGeneratorService pdfGeneratorService;

    public InvoiceServiceImpl(
            InvoiceRepository invoiceRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository,
            PdfGeneratorService pdfGeneratorService
    ) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Override
    @Transactional
    public InvoiceDTO generateForOrder(Long orderId) {
        // Idempotent: return existing invoice if present
        Optional<Invoice> existing = invoiceRepository.findByOrder_OrderId(orderId);
        if (existing.isPresent()) {
            return toDTO(existing.get());
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (order.getStatus() == null || !"COMPLETED".equalsIgnoreCase(order.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not completed");
        }

        // Payment must be confirmed
        List<Payment> payments = paymentRepository.findByOrder_OrderId(orderId);
        if (payments == null || payments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment not found for order");
        }
        boolean confirmed = payments.stream().anyMatch(p -> "CONFIRMED".equalsIgnoreCase(p.getStatus()));
        if (!confirmed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment not confirmed");
        }

        // Compute total from order items (fallback to order total if present)
        List<OrderItem> items = orderItemRepository.findByOrder_OrderId(orderId);
        BigDecimal computed = items.stream()
                .map(i -> i.getUnitPrice().multiply(new BigDecimal(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = order.getTotalAmount() != null && order.getTotalAmount().compareTo(BigDecimal.ZERO) > 0
                ? order.getTotalAmount() : computed;

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(30));
        invoice.setTotalAmount(total);

        Invoice saved = invoiceRepository.save(invoice);

        return toDTO(saved);
    }

    @Override
    public Optional<InvoiceDTO> getById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).map(this::toDTO);
    }

    @Override
    public Optional<InvoiceDTO> getByOrderId(Long orderId) {
        return invoiceRepository.findByOrder_OrderId(orderId).map(this::toDTO);
    }

    @Override
    public List<InvoiceDTO> getByInvoiceDate(LocalDate date) {
        return invoiceRepository.findByInvoiceDate(date).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getByDueDate(LocalDate date) {
        return invoiceRepository.findByDueDate(date).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getAll() {
        return invoiceRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public byte[] renderPdf(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        return pdfGeneratorService.generateInvoicePdf(invoice);
    }

    // ✅ Updated DTO Mapping to include order items
    private InvoiceDTO toDTO(Invoice inv) {
        List<Order_ItemDTO> orderItems = inv.getOrder().getOrderItems().stream()
                .map(item -> new Order_ItemDTO(
                		item.getQuantity(),
                		item.getUnitPrice(),
                		item.getOrderItemId(),
                		item.getName()
                ))
                .collect(Collectors.toList());

        return new InvoiceDTO(
                inv.getInvoiceId(),
                inv.getOrder() != null ? inv.getOrder().getOrderId() : null,
                inv.getInvoiceDate(),
                inv.getDueDate(),
                inv.getTotalAmount(),
                orderItems
        );
    }
}


































//package com.service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.http.HttpStatus;
//
//import com.dto.InvoiceDTO;
//import com.entity.Invoice;
//import com.entity.Order;
//import com.entity.OrderItem;
//import com.entity.Payment;
//import com.repository.InvoiceRepository;
//import com.repository.OrderItemRepository;
//import com.repository.OrderRepository;
//import com.repository.PaymentRepository;
//
//@Service
//public class InvoiceServiceImpl implements InvoiceService {
//
//    private final InvoiceRepository invoiceRepository;
//    private final OrderRepository orderRepository;
//    private final OrderItemRepository orderItemRepository;
//    private final PaymentRepository paymentRepository;
//    private final PdfGeneratorService pdfGeneratorService;
//
//    public InvoiceServiceImpl(
//            InvoiceRepository invoiceRepository,
//            OrderRepository orderRepository,
//            OrderItemRepository orderItemRepository,
//            PaymentRepository paymentRepository,
//            PdfGeneratorService pdfGeneratorService
//    ) {
//        this.invoiceRepository = invoiceRepository;
//        this.orderRepository = orderRepository;
//        this.orderItemRepository = orderItemRepository;
//        this.paymentRepository = paymentRepository;
//        this.pdfGeneratorService = pdfGeneratorService;
//    }
//
//    @Override
//    @Transactional
//    public InvoiceDTO generateForOrder(Long orderId) {
//        // Idempotent: return existing invoice if present
//        Optional<Invoice> existing = invoiceRepository.findByOrder_OrderId(orderId);
//        if (existing.isPresent()) {
//            return toDTO(existing.get());
//        }
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
//
//        if (order.getStatus() == null || !"COMPLETED".equalsIgnoreCase(order.getStatus())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not completed");
//        }
//
//        // Payment must be confirmed
//        List<Payment> payments = paymentRepository.findByOrder_OrderId(orderId);
//        if (payments == null || payments.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment not found for order");
//        }
//        boolean confirmed = payments.stream().anyMatch(p -> "CONFIRMED".equalsIgnoreCase(p.getStatus()));
//        if (!confirmed) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment not confirmed");
//        }
//
//        // Compute total from order items (fallback to order total if present)
//        List<OrderItem> items = orderItemRepository.findByOrder_OrderId(orderId);
//        BigDecimal computed = items.stream()
//                .map(i -> i.getUnitPrice().multiply(new BigDecimal(i.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal total = order.getTotalAmount() != null && order.getTotalAmount().compareTo(BigDecimal.ZERO) > 0
//                ? order.getTotalAmount() : computed;
//
//        Invoice invoice = new Invoice();
//        invoice.setOrder(order);
//        invoice.setInvoiceDate(LocalDate.now());
//        invoice.setDueDate(LocalDate.now().plusDays(30));
//        invoice.setTotalAmount(total);
//
//        Invoice saved = invoiceRepository.save(invoice);
//
//        return toDTO(saved);
//    }
//
//    @Override
//    public Optional<InvoiceDTO> getById(Long invoiceId) {
//        return invoiceRepository.findById(invoiceId).map(this::toDTO);
//    }
//
//    @Override
//    public Optional<InvoiceDTO> getByOrderId(Long orderId) {
//        return invoiceRepository.findByOrder_OrderId(orderId).map(this::toDTO);
//    }
//
//    @Override
//    public List<InvoiceDTO> getByInvoiceDate(LocalDate date) {
//        return invoiceRepository.findByInvoiceDate(date).stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<InvoiceDTO> getByDueDate(LocalDate date) {
//        return invoiceRepository.findByDueDate(date).stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<InvoiceDTO> getAll() {
//        return invoiceRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public byte[] renderPdf(Long invoiceId) {
//        Invoice invoice = invoiceRepository.findById(invoiceId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
//        return pdfGeneratorService.generateInvoicePdf(invoice);
//    }
//
//    private InvoiceDTO toDTO(Invoice inv) {
//        return new InvoiceDTO(
//            inv.getInvoiceId(),
//            inv.getOrder() != null ? inv.getOrder().getOrderId() : null,
//            inv.getInvoiceDate(),
//            inv.getDueDate(),
//            inv.getTotalAmount()
//        );
//    }
//}

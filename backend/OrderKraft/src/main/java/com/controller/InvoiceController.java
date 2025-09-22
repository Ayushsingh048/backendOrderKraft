package com.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.InvoiceDTO;
import com.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // 1) Admin triggers invoice generation
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<InvoiceDTO> generate(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.generateForOrder(orderId));
    }

    // 2) Get single invoice
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDTO> get(@PathVariable Long invoiceId) {
        Optional<InvoiceDTO> dto = invoiceService.getById(invoiceId);
        return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 3) Get by orderId
    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<InvoiceDTO> byOrder(@PathVariable Long orderId) {
        Optional<InvoiceDTO> dto = invoiceService.getByOrderId(orderId);
        return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 4) Search by dates
    @GetMapping("/search/by-invoice-date")
    public List<InvoiceDTO> byInvoiceDate(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return invoiceService.getByInvoiceDate(date);
    }

    @GetMapping("/search/by-due-date")
    public List<InvoiceDTO> byDueDate(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return invoiceService.getByDueDate(date);
    }

    // 5) List all
    @GetMapping
    public List<InvoiceDTO> all() {
        return invoiceService.getAll();
    }

    // 6) Download (on-the-fly generated) PDF
    @GetMapping("/{invoiceId}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long invoiceId) {
        byte[] pdf = invoiceService.renderPdf(invoiceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + invoiceId + ".pdf");
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
